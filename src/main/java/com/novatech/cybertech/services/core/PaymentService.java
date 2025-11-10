package com.novatech.cybertech.services.core;

import com.novatech.cybertech.entities.PaymentEntity;

public interface PaymentService {
    PaymentEntity processPayment(final PaymentEntity paymentEntity);
}
