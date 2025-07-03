package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.factory.ShippingStrategyFactory;
import com.novatech.cybertech.services.core.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingServieImp implements ShippingService {

    private final ShippingStrategyFactory shippingStrategyFactory;

    @Override
    public void shipOrder(OrderEntity orderEntity) {
        shippingStrategyFactory.getStrategy(orderEntity.getShippingType()).shipOrder(orderEntity);
    }
}
