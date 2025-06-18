package com.novatech.cybertech.events;

import com.novatech.cybertech.entities.OrderEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderCreatedEvent extends ApplicationEvent {

    private final OrderEntity order;

    public OrderCreatedEvent(Object source, OrderEntity order) {
        super(source);
        this.order = order;
    }
}
