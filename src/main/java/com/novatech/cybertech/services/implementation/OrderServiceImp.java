package com.novatech.cybertech.services.implementation;


import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderPlacingRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.entities.PaymentEntity;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.entities.enums.PaymentStatus;
import com.novatech.cybertech.events.OrderCreatedEvent;
import com.novatech.cybertech.exceptions.OrderNotFoundException;
import com.novatech.cybertech.exceptions.UserNotFoundException;
import com.novatech.cybertech.mappers.entity.OrderMapper;
import com.novatech.cybertech.repositories.OrderRepository;
import com.novatech.cybertech.repositories.UserRepository;
import com.novatech.cybertech.services.core.OrderService;
import com.novatech.cybertech.services.core.PaymentService;
import com.novatech.cybertech.validator.core.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderMapper orderMapper;
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
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
        OrderEntity orderEntity = orderMapper.mapFromCreationRequestToEntity(orderCreateRequestDto);

        orderValidatorChain.validate(orderEntity);

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        eventPublisher.publishEvent(new OrderCreatedEvent(this, savedOrder));

        return orderMapper.mapFromEntityToResponseDto(savedOrder);
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
    public OrderResponseDto placeOrder(OrderPlacingRequestDto orderPlacingRequestDto) {

        final OrderEntity orderEntity = orderMapper.mapFromOrderPlacingRequestDtoToOrderEntity(orderPlacingRequestDto);

        orderValidatorChain.validate(orderEntity);
        final UserEntity user = userRepository.findByUuid(orderPlacingRequestDto.getUserUuid()).orElseThrow(() -> new UserNotFoundException("User with the UUID : " + orderPlacingRequestDto.getUserUuid() + " was not found"));

        final PaymentStatus status = paymentService.processPayment(new PaymentEntity());//TODO: refactor this part to pass a real PaymentContext or PaymentDto object to this method

        //paymentFactory.getServiceFromPaymentType(orderPlacingRequestDto.getPaymentMethodType()).processPayment(user.ge)

        //TODO: ne pas oublier de créer un paymentEntity dans le cas d'une commande paypal et de la mettre en status pending

        orderValidatorChain.validate(orderEntity);

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        eventPublisher.publishEvent(new OrderCreatedEvent(this, savedOrder));

        return orderMapper.mapFromEntityToResponseDto(savedOrder);
    }
}
