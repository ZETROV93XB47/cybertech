package com.novatech.cybertech.dto.request.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderPaymentRequestDto {//TODO: revoir cette classe
    private String cvv;
    private String paypalToken;
    private PayPalPaymentRequestDto payPalPaymentRequestDto;
}
