package com.novatech.cybertech.listener;

import com.novatech.cybertech.annotation.CommunicationTypeHandler;
import com.novatech.cybertech.entities.NotificationContext;
import com.novatech.cybertech.entities.enums.CommunicationType;
import com.novatech.cybertech.services.implementation.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CommunicationTypeHandler(CommunicationType.EMAIL)
public class EmailNotificationProcessor implements NotificationProcessor {

    private final MailService emailService;

    @Override
    public void sendMessage(final NotificationContext notificationContext) {
        final Map<String, Object> model = (Map<String, Object>) notificationContext.getPayload();

        emailService.sendOrderConfirmationEmail(notificationContext.getUser().getEmail(), model);
    }
}
