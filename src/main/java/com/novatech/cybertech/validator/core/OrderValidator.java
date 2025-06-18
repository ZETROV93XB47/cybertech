package com.novatech.cybertech.validator.core;

import com.novatech.cybertech.entities.OrderEntity;

public interface OrderValidator {
    void validate(OrderEntity order);
}
