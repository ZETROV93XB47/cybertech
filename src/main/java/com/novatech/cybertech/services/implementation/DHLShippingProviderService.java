package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.data.ShippingContext;
import com.novatech.cybertech.services.core.ShippingStrategyProcessor;
import com.novatech.cybertech.services.core.AbstractShippingProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class DHLShippingProviderService extends AbstractShippingProvider {

    public DHLShippingProviderService(final ShippingStrategyProcessor shippingStrategyProcessor) {
        super(shippingStrategyProcessor);
    }

    @Override
    public void ship(final ShippingContext shippingContext) {

    }
}
