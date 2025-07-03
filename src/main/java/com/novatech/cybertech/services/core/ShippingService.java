package com.novatech.cybertech.services.core;

import com.novatech.cybertech.entities.OrderEntity;

public interface ShippingService {
    void shipOrder(final OrderEntity orderEntity);
}
