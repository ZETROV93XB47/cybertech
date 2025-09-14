package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.annotation.ShippingTypeHandler;
import com.novatech.cybertech.data.ShippingContext;
import com.novatech.cybertech.entities.enums.ShippingType;
import com.novatech.cybertech.services.core.ShippingStrategyProcessor;
import org.springframework.stereotype.Component;

@Component
@ShippingTypeHandler(ShippingType.STANDARD)
public class StandarShippingProcessor implements ShippingStrategyProcessor {
    @Override
    public void ship(final ShippingContext shippingContext) {

    }
}