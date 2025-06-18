package com.novatech.cybertech.factory;

import com.novatech.cybertech.entities.enums.PaymentType;
import com.novatech.cybertech.services.core.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentFactory {

    private final Map<PaymentType, PaymentService> serviceMap;

    public PaymentService getServiceFromPaymentType(final PaymentType type) {
        return serviceMap.get(type);
    }
}
