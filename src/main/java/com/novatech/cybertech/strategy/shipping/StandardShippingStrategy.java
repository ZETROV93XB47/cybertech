package com.novatech.cybertech.strategy.shipping;

import com.novatech.cybertech.annotation.ShippingTypeHandler;
import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.entities.enums.ShippingType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ShippingTypeHandler(ShippingType.STANDARD)
public class StandardShippingStrategy implements ShippingStrategy {
    @Override
    public BigDecimal calculateShippingCost(OrderEntity order) {
        return BigDecimal.valueOf(5.00);
    }

    @Override
    public void shipOrder(OrderEntity order) {

    }
}