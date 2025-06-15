package com.novatech.cybertech.services.implementation;


import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.exceptions.OrderNotFoundException;
import com.novatech.cybertech.mappers.entity.OrderMapper;
import com.novatech.cybertech.repositories.OrderRepository;
import com.novatech.cybertech.services.core.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderMapper productMapper;
    private final OrderRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<OrderResponseDto> getAll() {
        return productMapper.mapFromEntityToDto(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getByUUID(UUID uuid) {
        return productMapper.mapFromEntityToDto(productRepository.findByUuid(uuid).orElseThrow(() -> new OrderNotFoundException("No product with the UUID : " + uuid + " found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<OrderResponseDto> getByUUIDs(Collection<UUID> uuids) {
        return productMapper.mapFromEntityToDto(productRepository.findAllByUuidIn(uuids));
    }

    @Override
    @Transactional
    public OrderResponseDto create(OrderCreateRequestDto productCreateRequestDto) {
        return productMapper.mapFromEntityToDto(productRepository.save(productMapper.mapFromCreationRequestToEntity(productCreateRequestDto)));
    }

    @Transactional
    public Collection<OrderResponseDto> createAutomatically(Collection<OrderEntity> products) {
        return new ArrayList<>(productMapper.mapFromEntityToDto(products.stream().map(productRepository::save).toList()));
    }

    @Override
    @Transactional
    public OrderResponseDto update(final OrderUpdateRequestDto productCreateRequestDto) {
        return productMapper.mapFromEntityToDto(productRepository.save(productMapper.mapFromUpdateRequestToEntity(productCreateRequestDto)));
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID uuid) {
        productRepository.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public void deleteByUUIDs(Collection<UUID> uuids) {
        productRepository.deleteAllByUuidIn(uuids);
    }
}
