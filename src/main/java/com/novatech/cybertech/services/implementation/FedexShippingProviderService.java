package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.data.ShippingContext;
import com.novatech.cybertech.services.core.ShippingStrategyProcessor;
import com.novatech.cybertech.services.core.AbstractShippingProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FedexShippingProviderService extends AbstractShippingProvider {

    public FedexShippingProviderService(final ShippingStrategyProcessor shippingStrategyProcessor) {
        super(shippingStrategyProcessor);
    }

    @Override
    public void ship(final ShippingContext shippingContext) {

    }
}
