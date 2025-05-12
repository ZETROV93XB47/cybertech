package com.novatech.cybertech.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType implements EnumFunctions<Integer> {

    PAYPAL(1),
    CREDIT_CARD(2),
    APPLE_PAY(3),
    GOOGLE_PAY(4),
    CASH(5);

    private final Integer code;

    public static PaymentType getByCode(final Integer code) {
        return EnumFunctions.getByCode(code, PaymentType.class);
    }
}
