package com.novatech.cybertech.listener;

import com.novatech.cybertech.entities.NotificationContext;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public abstract class Notification {

    protected NotificationProcessor notificationProcessor;

    public Notification setNotificationProcessorAndRun(final NotificationProcessor notificationProcessor) {
        this.notificationProcessor = notificationProcessor;
        return this;
    }

    public Notification withNotificationProcessor(final NotificationProcessor notificationProcessor) {
        this.notificationProcessor = notificationProcessor;
        return this;
    }

    public abstract void sendNotification(final NotificationContext notificationContext);
}
