package com.novatech.cybertech.services.implementation;


import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderPlacingRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.request.orderItem.OrderItemCreateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.entities.PaymentEntity;
import com.novatech.cybertech.entities.ProductEntity;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.entities.enums.PaymentStatus;
import com.novatech.cybertech.events.OrderCreatedEvent;
import com.novatech.cybertech.exceptions.OrderNotFoundException;
import com.novatech.cybertech.exceptions.PaymentFailedException;
import com.novatech.cybertech.exceptions.UserNotFoundException;
import com.novatech.cybertech.mappers.entity.OrderMapper;
import com.novatech.cybertech.repositories.OrderRepository;
import com.novatech.cybertech.repositories.ProductRepository;
import com.novatech.cybertech.repositories.UserRepository;
import com.novatech.cybertech.services.core.OrderService;
import com.novatech.cybertech.services.core.PaymentService;
import com.novatech.cybertech.services.core.ShippingService;
import com.novatech.cybertech.validator.core.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderMapper orderMapper;

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final PaymentService paymentService;
    private final ShippingService shippingService;
    private final OrderValidator orderValidatorChain;
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
        //OrderEntity orderEntity = orderMapper.mapFromCreationRequestToEntity(orderCreateRequestDto);

        // orderValidatorChain.validate(orderEntity);

        //OrderEntity savedOrder = orderRepository.save(orderEntity);

        //eventPublisher.publishEvent(new OrderCreatedEvent(this, savedOrder));

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
    public OrderResponseDto placeOrder(OrderPlacingRequestDto orderPlacingRequestDto) {

        final OrderEntity orderEntity = orderMapper.mapFromOrderPlacingRequestDtoToOrderEntity(orderPlacingRequestDto);
        final UserEntity user = userRepository.findByUuid(orderPlacingRequestDto.getUserUuid()).orElseThrow(() -> new UserNotFoundException("User with the UUID : " + orderPlacingRequestDto.getUserUuid() + " was not found"));

        orderEntity.setUserEntity(user);

        orderValidatorChain.validate(orderEntity);

        final List<ProductEntity> productEntities = getAllProducts(orderPlacingRequestDto);

        BigDecimal totalPrice = processTotalPrice(productEntities);

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .amount(totalPrice)
                .paymentType(orderPlacingRequestDto.getPaymentMethodType())
                .paymentStatus(PaymentStatus.PENDING)
                //.orderEntity(orderEntity) //Not necessary here
                .paymentDate(LocalDateTime.now())
                .build();

        final PaymentStatus paymentStatus = paymentService.processPayment(paymentEntity);

        if (paymentStatus.equals(PaymentStatus.SUCCESS)) {

            paymentEntity.setPaymentStatus(paymentStatus);
            paymentEntity.setOrderEntity(orderEntity);//TODO: check if redundant
            orderEntity.setPaymentEntity(paymentEntity);

            final OrderEntity savedOrder = orderRepository.save(orderEntity);//TODO: check if i save the order entity, it will then save the paymentEntity automatically

            eventPublisher.publishEvent(new OrderCreatedEvent(this, savedOrder));

            shippingService.shipOrder(savedOrder);
            
            return orderMapper.mapFromEntityToResponseDto(savedOrder);
        }

        throw new PaymentFailedException("Could not process the payment for this order, please check your balance");
        //TODO: ne pas oublier de créer un paymentEntity dans le cas d'une commande paypal et de la mettre en status pending
    }

    @Override
    public List<OrderResponseDto> findAllByUserUuid(final UUID userUuid) {
        return (List<OrderResponseDto>) orderMapper.mapFromEntityToResponseDto(userRepository.findByUuid(userUuid).orElseThrow(() -> new UserNotFoundException("Can't retrieve user with this uuid")).getOrderEntities());
    }

    private final BigDecimal processTotalPrice(final List<ProductEntity> productEntities) {
        return BigDecimal.valueOf(productEntities.stream().mapToInt(productEntity -> productEntity.getPrice().intValue()).sum());
    }

    private final List<ProductEntity> getAllProducts(final OrderPlacingRequestDto orderPlacingRequestDto) {
        return productRepository.findAllByUuidIn(orderPlacingRequestDto.getOrderItems().stream().map(OrderItemCreateRequestDto::getProductUuid).toList());
    }
}
