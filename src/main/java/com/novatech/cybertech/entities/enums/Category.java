package com.novatech.cybertech.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum Category {

    GAMING(1),
    NOTEBOOK(2);

    private final Integer code;

    public static Category getByCode(int code) {
        return stream(values())
                .filter(category -> category.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
