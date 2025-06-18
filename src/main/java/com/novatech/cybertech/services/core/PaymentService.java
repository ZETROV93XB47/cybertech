package com.novatech.cybertech.services.core;

import com.novatech.cybertech.entities.PaymentEntity;
import com.novatech.cybertech.entities.enums.PaymentStatus;

public interface PaymentService {
    PaymentStatus processPayment(PaymentEntity payment);
}

