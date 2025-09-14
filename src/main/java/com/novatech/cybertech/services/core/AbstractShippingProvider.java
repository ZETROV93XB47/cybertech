package com.novatech.cybertech.services.core;

import com.novatech.cybertech.data.ShippingContext;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@AllArgsConstructor
public abstract class AbstractShippingProvider {
    protected ShippingStrategyProcessor shippingStrategyProcessor;

    public AbstractShippingProvider setShippingTypeProcessorAndRun(final ShippingStrategyProcessor shippingStrategyProcessor) {
        this.shippingStrategyProcessor = shippingStrategyProcessor;
        return this;
    }

    public AbstractShippingProvider withShippingStrategyProcessor(final ShippingStrategyProcessor shippingStrategyProcessor) {
        this.shippingStrategyProcessor = shippingStrategyProcessor;
        return this;
    }

    public abstract void ship(final ShippingContext orderEntity);
}
