package com.novatech.cybertech.dto.request.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PayPalPaymentRequestDto {
    private String currency;
    private BigDecimal amount;
    private String description;
    private String cancelUrl;
    private String successUrl;
}
