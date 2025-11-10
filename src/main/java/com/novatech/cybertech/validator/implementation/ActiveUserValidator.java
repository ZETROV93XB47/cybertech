package com.novatech.cybertech.validator.implementation;

import com.novatech.cybertech.data.OrderValidationDto;
import com.novatech.cybertech.entities.BaseEntity;
import com.novatech.cybertech.entities.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class ActiveUserValidator extends ChainableOrderValidator {

    @Override
    public void validate(final OrderValidationDto orderValidationDto) {

        if (!orderValidationDto.isUserActive()) {
            throw new IllegalStateException("Utilisateur inactif !");
        }
        nextStep(orderValidationDto);
    }
}
