package com.novatech.cybertech.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

//@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class OrderItem {

    private UUID orderItemUuid;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;

    private Order order;

    private Product productId;
}
