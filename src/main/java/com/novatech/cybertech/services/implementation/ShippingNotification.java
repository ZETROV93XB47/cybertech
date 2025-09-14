package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.annotation.NotificationTypeHandler;
import com.novatech.cybertech.data.NotificationContext;
import com.novatech.cybertech.entities.enums.NotificationType;
import com.novatech.cybertech.services.core.NotificationProcessor;
import com.novatech.cybertech.services.core.AbstractNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NotificationTypeHandler(NotificationType.SHIPPING_CONFIRMATION)
public class ShippingNotification extends AbstractNotification {

    public ShippingNotification(NotificationProcessor notificationProcessor) {
        super(notificationProcessor);
    }

    @Override
    public void sendNotification(NotificationContext notificationContext) {

    }
}
