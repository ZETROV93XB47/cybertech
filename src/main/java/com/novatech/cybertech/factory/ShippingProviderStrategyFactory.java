package com.novatech.cybertech.factory;

import com.novatech.cybertech.entities.enums.ShippingProvider;
import com.novatech.cybertech.services.core.AbstractShippingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ShippingProviderStrategyFactory {

    private final Map<ShippingProvider, AbstractShippingProvider> strategyMap;

    public AbstractShippingProvider getStrategy(final ShippingProvider shippingProvider) {
        return strategyMap.get(shippingProvider);
    }
}
