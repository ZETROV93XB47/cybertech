package com.novatech.cybertech.dto.data;

import com.novatech.cybertech.dto.request.orderItem.OrderItemCreateRequestDto;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class OrderReservationDto {
    private final UUID orderUuid;
    private final Map<UUID, Integer> productsByQuantityMap;
}
