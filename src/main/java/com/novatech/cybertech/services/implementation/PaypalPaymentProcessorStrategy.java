package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.annotation.PaymentTypeHandler;
import com.novatech.cybertech.entities.PaymentEntity;
import com.novatech.cybertech.entities.enums.PaymentStatus;
import com.novatech.cybertech.entities.enums.PaymentType;
import com.novatech.cybertech.repositories.PaymentRepository;
import com.novatech.cybertech.services.core.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@PaymentTypeHandler(PaymentType.PAYPAL)
public class PaypalPaymentProcessorStrategy implements PaymentProcessor {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentStatus processPayment(final PaymentEntity payment) {
        // logiques spécifiques à PayPal

        paymentRepository.save(payment);

        log.info("Processing PayPal payment for amount: {}", payment.getAmount());

        return payment.getPaymentStatus();
    }
}
