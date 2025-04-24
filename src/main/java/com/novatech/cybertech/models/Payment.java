package com.novatech.cybertech.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Payment {

    private UUID paymentUuid;

    private BigDecimal amount;

    private String paymentMethod;  // "CREDIT_CARD", "PAYPAL", etc.

    private String paymentStatus;  // "SUCCESS", "FAILED", etc.

    private Order order;

    @CreationTimestamp
    private LocalDateTime paymentDate;
}
