package com.novatech.cybertech.api.controllers.spec;

import java.util.UUID;

public interface PayPalPaymentControllerApiSpec {
    void onPaymentSuccess(final UUID orderUUID);
    void onPaymentFailure();
}
