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
@PaymentTypeHandler(PaymentType.CREDIT_CARD)
public class CreditCardPaymentProcessorStrategy implements PaymentProcessor {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentStatus processPayment(PaymentEntity payment) {

        //Call to the users's bank to process the payment
        //This part will be implmemented correctly later, this code is written just to simulate a call to an external service (the user's bank)

        if (payment.getAmount().intValueExact() % 2 != 0) {
             log.info("calling user's bank to process the payment");
            log.info("Payment processed successfully");

            payment.setPaymentStatus(PaymentStatus.SUCCESS);

            paymentRepository.save(payment);
            log.info("Processing credit card payment for amount:  {}", payment.getAmount());
        }
        else {
            log.info("calling user's bank to process the payment");
            log.info("Payment failed");
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }


        return payment.getPaymentStatus();
    }
}
