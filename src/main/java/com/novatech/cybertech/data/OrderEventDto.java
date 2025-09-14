package com.novatech.cybertech.data;

import com.novatech.cybertech.entities.enums.OrderStatus;
import com.novatech.cybertech.entities.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class OrderEventDto {
    private UUID orderUuid;
    private String customerName;
    private String customerEmail;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private UserDto userDto;
    private OrderDto orderDto;
    private List<OrderItemDto> orderItemDtoList;
    private Map<UUID, Integer> productsByQuantityMap;

    //TODO: getOrderItemEntities
}
