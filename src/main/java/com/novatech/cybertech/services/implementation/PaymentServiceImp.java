package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.entities.PaymentEntity;
import com.novatech.cybertech.entities.enums.PaymentStatus;
import com.novatech.cybertech.factory.PaymentStrategyFactory;
import com.novatech.cybertech.services.core.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    private final PaymentStrategyFactory paymentStrategyFactory;

    @Override
    public PaymentEntity processPayment(PaymentEntity paymentEntity) {
        return paymentStrategyFactory.getServiceFromPaymentType(paymentEntity.getPaymentType()).processPayment(paymentEntity);
    }
}
