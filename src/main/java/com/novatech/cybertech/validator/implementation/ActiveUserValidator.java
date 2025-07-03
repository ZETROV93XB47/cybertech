package com.novatech.cybertech.validator.implementation;

import com.novatech.cybertech.entities.BaseEntity;
import com.novatech.cybertech.entities.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class ActiveUserValidator extends ChainableOrderValidator {

    @Override
    public void validate(final BaseEntity entity) {
        final OrderEntity order = (OrderEntity) entity;

        if (!order.getUserEntity().getIsActive().equals(true)) {
            throw new IllegalStateException("Utilisateur inactif !");
        }
        nextStep(order);
    }
}
