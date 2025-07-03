package com.novatech.cybertech.dto.request.order;

import com.novatech.cybertech.entities.enums.PaymentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderPaymentRequestDto {
    private String cvv;
    private String paypalToken;
    private PaymentType paymentType;
    private PayPalPaymentRequestDto payPalPaymentRequestDto;
}
