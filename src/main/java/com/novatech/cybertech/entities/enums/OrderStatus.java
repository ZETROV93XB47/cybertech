package com.novatech.cybertech.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    //PENDING_PAYMENT(1),
    PROCESSING(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5),
    REFUNDED(6);

    private final Integer code;

    public static OrderStatus getByCode(final int code) {
        return stream(values()).filter(marque -> marque.getCode() == code).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}