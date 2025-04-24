package com.novatech.cybertech.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING(1),
    SHIPPED(2),
    DELIVERED(3),
    CANCELLED(4);

    private final Integer code;

    public static OrderStatus getByCode(final int code) {
        return stream(values()).filter(marque -> marque.getCode() == code).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
