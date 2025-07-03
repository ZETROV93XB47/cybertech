package com.novatech.cybertech.strategy.shipping;

import com.novatech.cybertech.entities.OrderEntity;

import java.math.BigDecimal;

public interface ShippingStrategy {
    BigDecimal calculateShippingCost(final OrderEntity order);
    void shipOrder(final OrderEntity order);
}
