package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.annotation.CommunicationTypeHandler;
import com.novatech.cybertech.dto.data.EmailDto;
import com.novatech.cybertech.dto.data.NotificationContext;
import com.novatech.cybertech.entities.enums.CommunicationChanel;
import com.novatech.cybertech.services.core.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Primary
@RequiredArgsConstructor
@Component("emailNotificationProcessor")
@CommunicationTypeHandler(CommunicationChanel.EMAIL)
public class EmailNotificationProcessor implements NotificationProcessor {

    private final MailService emailService;

    @Override
    public void sendMessage(final NotificationContext notificationContext) {

        //final NotificationType notificationType = notificationContext.getNotificationType();
//        final OrderEventDto orderEventDto = (OrderEventDto) notificationContext.getContext();
//        final UserDto user = orderEventDto.getUserDto();

        final EmailDto emailDto = EmailDto.builder()
                .from("abc@mail.com")
                .to(notificationContext.getUser().getEmail())
                .subject(notificationContext.getMessage())
                .context((Map<String, Object>)notificationContext.getPayload())
                .build();

        emailService.sendEmail(emailDto);
    }
}
