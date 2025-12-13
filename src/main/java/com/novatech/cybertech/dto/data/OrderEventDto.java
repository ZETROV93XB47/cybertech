package com.novatech.cybertech.dto.data;

import com.novatech.cybertech.entities.enums.OrderStatus;
import com.novatech.cybertech.entities.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class OrderEventDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID orderUuid;
    private UserDto userDto;
    private String customerName;
    private String customerEmail;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private Map<UUID, Integer> productsByQuantityMap;
}

//private OrderDto orderDto;//
//private List<OrderItemDto> orderItemDtoList;//
//TODO: getOrderItemEntities
