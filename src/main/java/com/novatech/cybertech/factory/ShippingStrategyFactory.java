package com.novatech.cybertech.factory;

import com.novatech.cybertech.entities.enums.ShippingType;
import com.novatech.cybertech.strategy.shipping.ShippingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ShippingStrategyFactory {

    private final Map<ShippingType, ShippingStrategy> strategyMap;

    public ShippingStrategy getStrategy(ShippingType type) {
        return strategyMap.get(type);
    }
}

