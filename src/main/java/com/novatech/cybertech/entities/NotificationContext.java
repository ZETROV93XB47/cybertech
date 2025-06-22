package com.novatech.cybertech.entities;


import com.novatech.cybertech.entities.enums.CommunicationType;
import com.novatech.cybertech.entities.enums.NotificationType;
import lombok.*;

@With
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class NotificationContext {
    private final Object payload;
    private final String message;
    private final UserEntity user;
    private final NotificationType notificationType;
    private final CommunicationType communicationType;
    private final NotificationProcessorData notificationProcessorData;
}

