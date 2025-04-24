package com.novatech.cybertech.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID paymentUuid;

    private BigDecimal amount;
    private String paymentMethod;  // "CREDIT_CARD", "PAYPAL", etc.
    private String paymentStatus;  // "SUCCESS", "FAILED", etc.

    @OneToOne(mappedBy = "paymentEntity")
    private OrderEntity orderEntity;

    @CreationTimestamp
    private LocalDateTime paymentDate;
}
