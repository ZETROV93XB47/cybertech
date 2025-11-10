package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.annotation.CommunicationTypeHandler;
import com.novatech.cybertech.data.EmailDto;
import com.novatech.cybertech.data.NotificationContext;
import com.novatech.cybertech.entities.enums.CommunicationChanel;
import com.novatech.cybertech.entities.enums.NotificationType;
import com.novatech.cybertech.services.core.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@RequiredArgsConstructor
@Component("emailNotificationProcessor")
@CommunicationTypeHandler(CommunicationChanel.EMAIL)
public class EmailNotificationProcessor implements NotificationProcessor {

    private final MailService emailService;

    @Override
    public void sendMessage(final NotificationContext notificationContext) {

        final NotificationType notificationType = notificationContext.getNotificationType();

        final String mailSubject = switch (notificationType) {
            case ORDER_CONFIRMATION -> "Order Confirmation Email";
            case SHIPPING_CONFIRMATION -> "Shipping Confirmation Email";
            case PAYMENT_CONFIRMATION -> "Payment Confirmation Email";
        };

        final EmailDto emailDto = EmailDto.builder()
                .from("abc@mail.com")
                .to(notificationContext.getUser().getEmail())
                .subject(mailSubject)
                .payload(notificationContext.getPayload())
                .build();

        emailService.sendEmail(emailDto);
    }
}
