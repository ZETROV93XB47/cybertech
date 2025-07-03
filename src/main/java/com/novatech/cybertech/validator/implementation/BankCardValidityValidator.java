package com.novatech.cybertech.validator.implementation;

import com.novatech.cybertech.entities.BankCardEntity;
import com.novatech.cybertech.entities.BaseEntity;
import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.exceptions.BankCardExpiredException;
import com.novatech.cybertech.exceptions.NoDefaultBankCartSetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.novatech.cybertech.utils.DateConverter.convertExpiryDateToLocalDate;

@Slf4j
@Component
public class BankCardValidityValidator extends ChainableOrderValidator {

    @Override
    public void validate(final BaseEntity entity) {

        final OrderEntity order = (OrderEntity) entity;

        final BankCardEntity bankCardEntity = order
                .getUserEntity()
                .getBankCardEntities()
                .stream()
                .filter(b -> b.getIsDefault().equals(true))
                .findFirst()
                .orElseThrow(() -> new NoDefaultBankCartSetException("No default bank card set, please, set a default bank card and retry ..."));

        if (LocalDate.now().isAfter(convertExpiryDateToLocalDate(bankCardEntity.getExpiryDate())))
            throw new BankCardExpiredException("Bank card expired");
        log.info("Bank card valid");
        nextStep(order);
    }
}
