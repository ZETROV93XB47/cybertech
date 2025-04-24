package com.novatech.cybertech.models;

import com.novatech.cybertech.entities.enums.OrderStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Order {

    private UUID orderUuid;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private User user;

    private Payment payment;

    private List<OrderItem> orderItems;
}
