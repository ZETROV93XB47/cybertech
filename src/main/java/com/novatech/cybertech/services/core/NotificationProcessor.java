package com.novatech.cybertech.services.core;

import com.novatech.cybertech.data.NotificationContext;

public interface NotificationProcessor {
    void sendMessage(final NotificationContext notificationContext);
}
