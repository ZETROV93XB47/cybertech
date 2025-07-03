package com.novatech.cybertech.services.core;

import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderPlacingRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService extends CrudBaseService<UUID, OrderCreateRequestDto, OrderUpdateRequestDto, OrderResponseDto> {
    OrderResponseDto placeOrder(final OrderPlacingRequestDto orderPlacingRequestDto);
    List<OrderResponseDto> findAllByUserUuid(UUID userUuid);
}
