package com.novatech.cybertech.factory;

import com.novatech.cybertech.entities.enums.ShippingType;
import com.novatech.cybertech.services.core.ShippingStrategyProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ShippingTypeProcessorStrategyFactory {

    private final Map<ShippingType, ShippingStrategyProcessor> strategyMap;

    public ShippingStrategyProcessor getStrategy(final ShippingType type) {
        return strategyMap.get(type);
    }
}
