package com.novatech.cybertech.api.controllers;

import com.novatech.cybertech.api.controllers.spec.PayPalPaymentControllerApiSpec;
import com.novatech.cybertech.services.core.PayPalPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.novatech.cybertech.constants.CyberTechAppConstants.PAYPAL_PAYMENT_CONTROLLER_BASE_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PAYPAL_PAYMENT_CONTROLLER_BASE_PATH)
@Tag(name = "PayPalPaymentController", description = "Endpoint to check if the user payment was successful or failed")
public class PayPalPaymentController implements PayPalPaymentControllerApiSpec {

    private final PayPalPaymentService payPalPaymentService;

    @Override
    public void onPaymentSuccess(@RequestParam("token") final UUID orderUUID) {
        payPalPaymentService.onSuccess(orderUUID);
    }

    @Override
    public void onPaymentFailure() {
        payPalPaymentService.onFailure();
    }
}
