package com.novatech.cybertech.services.implementation;


import com.novatech.cybertech.dto.data.OrderEventDto;
import com.novatech.cybertech.dto.data.OrderValidationDto;
import com.novatech.cybertech.dto.data.ShippingContext;
import com.novatech.cybertech.dto.data.UserDto;
import com.novatech.cybertech.dispatcher.ShippingDispatcher;
import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderPlacingRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.request.orderItem.OrderItemCreateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import com.novatech.cybertech.entities.*;
import com.novatech.cybertech.entities.enums.PaymentStatus;
import com.novatech.cybertech.events.OrderCreatedEvent;
import com.novatech.cybertech.exceptions.NoDefaultBankCartSetException;
import com.novatech.cybertech.exceptions.OrderNotFoundException;
import com.novatech.cybertech.exceptions.PaymentFailedException;
import com.novatech.cybertech.exceptions.UserNotFoundException;
import com.novatech.cybertech.mappers.entity.OrderMapper;
import com.novatech.cybertech.repositories.OrderRepository;
import com.novatech.cybertech.repositories.ProductRepository;
import com.novatech.cybertech.repositories.UserRepository;
import com.novatech.cybertech.services.core.OrderService;
import com.novatech.cybertech.services.core.PaymentService;
import com.novatech.cybertech.services.core.StockService;
import com.novatech.cybertech.validator.core.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.novatech.cybertech.entities.enums.DiscountType.NO_DISCOUNT;
import static com.novatech.cybertech.entities.enums.OrderStatus.PROCESSING;
import static com.novatech.cybertech.entities.enums.PaymentStatus.SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderMapper orderMapper;
    private final StockService stockService;

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final PaymentService paymentService;
    private final OrderValidator orderValidatorChain;
    private final ShippingDispatcher shippingDispatcher;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional(readOnly = true)
    public Collection<OrderResponseDto> getAll() {
        return orderMapper.mapFromEntityToResponseDto(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getByUUID(UUID uuid) {
        return orderMapper.mapFromEntityToResponseDto(orderRepository.findByUuid(uuid).orElseThrow(() -> new OrderNotFoundException("No product with the UUID : " + uuid + " found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<OrderResponseDto> getByUUIDs(Collection<UUID> uuids) {
        return orderMapper.mapFromEntityToResponseDto(orderRepository.findAllByUuidIn(uuids));
    }

    @Override
    @Transactional
    public OrderResponseDto create(OrderCreateRequestDto orderCreateRequestDto) {

        return orderMapper.mapFromEntityToResponseDto(orderRepository.save(orderMapper.mapFromCreationRequestToEntity(orderCreateRequestDto)));
    }

    @Transactional
    public Collection<OrderResponseDto> createAutomatically(Collection<OrderEntity> products) {
        return new ArrayList<>(orderMapper.mapFromEntityToResponseDto(products.stream().map(orderRepository::save).toList()));
    }

    @Override
    @Transactional
    public OrderResponseDto update(final OrderUpdateRequestDto productCreateRequestDto) {
        return orderMapper.mapFromEntityToResponseDto(orderRepository.save(orderMapper.mapFromUpdateRequestToEntity(productCreateRequestDto)));
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID uuid) {
        orderRepository.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public void deleteByUUIDs(Collection<UUID> uuids) {
        orderRepository.deleteAllByUuidIn(uuids);
    }


    @Override
    @Transactional
    public OrderResponseDto placeOrder(OrderPlacingRequestDto req, String jwt) {

        // 1. Charger l'utilisateur
        UserEntity userEntity = userRepository.findByUuid(req.getUserUuid())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // 2. Charger les produits
        List<ProductEntity> products = getAllProductsFromRequest(req);
        Map<UUID, Integer> quantities = req.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItemCreateRequestDto::getProductUuid, OrderItemCreateRequestDto::getQuantity));

        final UUID orderUUID = UUID.randomUUID();

        // 3. Réserver le stock AVANT tout paiement
        stockService.reserveStock(orderUUID, quantities);

        // 4. Vérifier la commande
        validateOrderBeforeProcessingPayment(quantities, userEntity);

        // 5. Calculer le prix total
        BigDecimal amount = processOrderPaymentAmount(req, products);

        // 6. Paiement
        PaymentEntity payment = paymentService.processPayment(req.getPaymentType(), amount);

        // 7. Construire l’OrderEntity
        OrderEntity order = orderMapper.mapFromOrderPlacingRequestDtoToOrderEntity(req);
        List<OrderItemEntity> items = getOrderItemEntities(req, order, products);
        initOrderEntity(orderUUID, order, payment, amount, items, userEntity);

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {

            // 8. Commit du stock
            stockService.commitStock(orderUUID);

            OrderEntity saved = orderRepository.save(order);

            // 9. Événements
            sendOrderCreationEvent(order, userEntity, amount, quantities);
            sendOrderShippingEvent(req, userEntity, saved);

            return orderMapper.mapFromEntityToResponseDto(saved);
        }

        else {

            // Paiement échec → libérer le stock
            stockService.releaseStock(orderUUID);
            throw new PaymentFailedException("Could not process payment");
        }
    }



    private void validateOrderBeforeProcessingPayment(Map<UUID, Integer> productsByQuantityMap, UserEntity userEntity) {
        final OrderValidationDto orderValidationDto = OrderValidationDto.builder()
                .isUserActive(userEntity.getIsActive())
                .productsByQuantityMap(productsByQuantityMap)
                .userDefaultBankCard(userEntity.getBankCardEntities().stream().filter(BankCardEntity::getIsDefault).findFirst().orElseThrow(() -> new NoDefaultBankCartSetException("No default bank card set, please, set a default bank card and retry ...")))
                .build();

        orderValidatorChain.validate(orderValidationDto);
    }

    private static void initOrderEntity(UUID orderUUID, OrderEntity orderEntity, PaymentEntity paymentEntity, BigDecimal totalPrice, List<OrderItemEntity> orderItemEntities, UserEntity user) {
        orderEntity.setUuid(orderUUID);
        orderEntity.setPaymentEntity(paymentEntity);
        orderEntity.setTotalAmount(totalPrice);
        orderEntity.setStatus(PROCESSING);
        orderEntity.setOrderItemEntities(orderItemEntities);
        orderEntity.setUserEntity(user);
        orderEntity.setDiscountType(NO_DISCOUNT);
    }

    private OrderResponseDto onPaymentSuccess(OrderPlacingRequestDto orderPlacingRequestDto, OrderEntity orderEntity, UserEntity user, BigDecimal totalPrice) {
        final OrderEntity savedOrder = orderRepository.save(orderEntity);//TODO: check if i save the order entity, it will then save the paymentEntity automatically

        final Map<UUID, Integer> productsByQuantityMap = orderPlacingRequestDto.getOrderItems().stream().collect(Collectors.toMap(OrderItemCreateRequestDto::getProductUuid, OrderItemCreateRequestDto::getQuantity));

        sendOrderCreationEvent(orderEntity, user, totalPrice, productsByQuantityMap);
        sendOrderShippingEvent(orderPlacingRequestDto, user, savedOrder);

        return orderMapper.mapFromEntityToResponseDto(savedOrder);
    }

    private void sendOrderShippingEvent(OrderPlacingRequestDto orderPlacingRequestDto, UserEntity user, OrderEntity savedOrder) {
        final ShippingContext shippingContext = ShippingContext.builder()
                .user(user)
                .payload(savedOrder)
                .shippingType(orderPlacingRequestDto.getShippingType())
                .shippingProvider(orderPlacingRequestDto.getShippingProvider())
                .build();

        shippingDispatcher.dispatch(shippingContext);
    }

    private void sendOrderCreationEvent(OrderEntity orderEntity, UserEntity user, BigDecimal totalPrice, Map<UUID, Integer> productsByQuantityMap) {

        OrderEventDto orderEventDto = OrderEventDto.builder()
                .orderUuid(orderEntity.getUuid())
                .orderStatus(orderEntity.getStatus())
                .paymentStatus(SUCCESS)
                .totalAmount(totalPrice)
                .productsByQuantityMap(productsByQuantityMap)
                .userDto(UserDto.builder()
                        .defaultCommunicationChanel(user.getFavoriteCommunicationChanel())
                        .email(user.getEmail())
                        .name(user.getFirstName())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .build();

        eventPublisher.publishEvent(new OrderCreatedEvent(this, orderEventDto));
    }

    private static List<OrderItemEntity> getOrderItemEntities(OrderPlacingRequestDto orderPlacingRequestDto, OrderEntity orderEntity, List<ProductEntity> productEntities) {
        final Map<UUID, Integer> productsByQuantityMap = orderPlacingRequestDto.getOrderItems().stream().collect(Collectors.toMap(OrderItemCreateRequestDto::getProductUuid, OrderItemCreateRequestDto::getQuantity));

        return productEntities.stream().map(productEntity -> OrderItemEntity.builder()
                        .uuid(UUID.randomUUID())
                        .unitPrice(productEntity.getPrice())
                        .quantity(productsByQuantityMap.get(productEntity.getUuid()))
                        .subtotal(productEntity.getPrice().multiply(BigDecimal.valueOf(productsByQuantityMap.get(productEntity.getUuid()))))
                        .orderEntity(orderEntity)//!Warning: Ici, l'objet orderEntity n'est pas encore complet
                        .productEntity(productEntity)
                        .build())
                .collect(Collectors.toList());
    }

//    private OrderResponseDto onPaymentPending(OrderPlacingRequestDto orderPlacingRequestDto, PaymentStatus paymentStatus, OrderEntity orderEntity, UserEntity user, BigDecimal totalPrice) {
//        //paymentEntity.setPaymentStatus(paymentStatus);
//        //paymentEntity.setOrderEntity(orderEntity);//TODO: check if redundant
//        //orderEntity.setPaymentEntity(paymentEntity);
//
//        final OrderEntity savedOrder = orderRepository.save(orderEntity);//TODO: check if i save the order entity, it will then save the paymentEntity automatically
//
//        final OrderEventDto orderEventDto = OrderEventDto.builder()
//                .build();
//
//        eventPublisher.publishEvent(new OrderCreatedEvent(this, orderEventDto));
//
//        sendOrderShippingEvent(orderPlacingRequestDto, user, savedOrder);
//
//        return orderMapper.mapFromEntityToResponseDto(savedOrder);
//    }


    private OrderResponseDto onPaymentFailure(OrderPlacingRequestDto orderPlacingRequestDto, OrderEntity orderEntity, UserEntity user, BigDecimal totalPrice) {

        final OrderEntity savedOrder = orderRepository.save(orderEntity);//TODO: check if i save the order entity, it will then save the paymentEntity automatically

        throw new PaymentFailedException("Could not process the payment for this order");

        /*
        final OrderEventDto orderEventDto = OrderEventDto.builder()
                .build();

        eventPublisher.publishEvent(new OrderCreatedEvent(this, orderEventDto));

        sendOrderShippingEvent(orderPlacingRequestDto, user, savedOrder);

        return orderMapper.mapFromEntityToResponseDto(savedOrder);

         */
    }


    @Override
    public List<OrderResponseDto> findAllByUserUuid(final UUID userUuid) {
        return (List<OrderResponseDto>) orderMapper.mapFromEntityToResponseDto(userRepository.findByUuid(userUuid).orElseThrow(() -> new UserNotFoundException("Can't retrieve user with this uuid")).getOrderEntities());
    }

    private BigDecimal processOrderPaymentAmount(final OrderPlacingRequestDto orderPlacingRequestDto, final List<ProductEntity> productEntities) {

        final Map<UUID, ProductEntity> productsByUuid = productEntities.stream().collect(Collectors.toMap(ProductEntity::getUuid, product -> product));

        return orderPlacingRequestDto.getOrderItems().stream()
                .map(orderItem -> {
                    ProductEntity product = productsByUuid.get(orderItem.getProductUuid());
                    if (product == null) {
                        log.warn("Product with UUID {} from order request not found in fetched products.", orderItem.getProductUuid());
                        return BigDecimal.ZERO;
                    }

                    return product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private List<ProductEntity> getAllProductsFromRequest(final OrderPlacingRequestDto orderPlacingRequestDto) {
        return productRepository.findAllByUuidIn(orderPlacingRequestDto.getOrderItems().stream().map(OrderItemCreateRequestDto::getProductUuid).toList());
    }
}
