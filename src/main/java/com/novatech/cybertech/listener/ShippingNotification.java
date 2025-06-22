package com.novatech.cybertech.listener;

import com.novatech.cybertech.annotation.NotificationTypeHandler;
import com.novatech.cybertech.entities.NotificationContext;
import com.novatech.cybertech.entities.enums.NotificationType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotificationTypeHandler(NotificationType.SHIPPING_CONFIRMATION)
public class ShippingNotification extends Notification {

    public ShippingNotification(NotificationProcessor notificationProcessor) {
        super(notificationProcessor);
    }

    @Override
    public void sendNotification(NotificationContext notificationContext) {

    }
}
