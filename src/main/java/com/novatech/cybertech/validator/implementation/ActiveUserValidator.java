package com.novatech.cybertech.validator.implementation;

import com.novatech.cybertech.entities.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class ActiveUserValidator extends ChainableOrderValidator {
    @Override
    public void validate(OrderEntity order) {
        if (!order.getUserEntity().getIsActive()) {
            throw new IllegalStateException("Utilisateur inactif !");
        }
        next(order);
    }
}
