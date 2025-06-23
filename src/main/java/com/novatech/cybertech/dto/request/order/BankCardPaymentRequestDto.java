package com.novatech.cybertech.dto.request.order;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BankCardPaymentRequestDto {
    private final String cvv;
}
