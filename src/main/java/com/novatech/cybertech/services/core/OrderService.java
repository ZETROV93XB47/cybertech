package com.novatech.cybertech.services.core;

import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;

import java.util.UUID;

public interface OrderService extends BaseService<UUID, OrderCreateRequestDto, OrderUpdateRequestDto, OrderResponseDto> {
}
