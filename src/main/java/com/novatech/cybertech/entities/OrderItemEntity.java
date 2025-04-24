package com.novatech.cybertech.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "orderItem")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private UUID orderItemUuid;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order", nullable = false)
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", nullable = false)
    private ProductEntity productEntityId;
}
