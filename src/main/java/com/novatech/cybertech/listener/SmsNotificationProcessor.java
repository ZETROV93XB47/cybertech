package com.novatech.cybertech.listener;

import com.novatech.cybertech.annotation.CommunicationTypeHandler;
import com.novatech.cybertech.entities.NotificationContext;
import com.novatech.cybertech.entities.enums.CommunicationType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CommunicationTypeHandler(CommunicationType.SMS)
public class SmsNotificationProcessor implements NotificationProcessor {

    @Override
    public void sendMessage(NotificationContext message) {

    }
}