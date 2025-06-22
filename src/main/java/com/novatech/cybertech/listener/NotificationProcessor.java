package com.novatech.cybertech.listener;

import com.novatech.cybertech.entities.NotificationContext;

public interface NotificationProcessor {
    void sendMessage(final NotificationContext notificationContext);
}
