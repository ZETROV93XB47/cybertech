package com.novatech.cybertech.services.core;

import com.novatech.cybertech.data.ShippingContext;

public interface ShippingStrategyProcessor {
    void ship(final ShippingContext shippingContext);
}
