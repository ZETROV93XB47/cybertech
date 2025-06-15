package com.novatech.cybertech.dto.response.order;

import com.novatech.cybertech.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private UUID orderUuid;
    private UUID userUuid;
    private Date orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String shippingAddress;

    private List<OrderItemResponseDto> items;
}
