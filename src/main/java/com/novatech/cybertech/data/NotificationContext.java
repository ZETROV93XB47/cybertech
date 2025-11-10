package com.novatech.cybertech.data;


import com.novatech.cybertech.entities.enums.CommunicationChanel;
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
    private final UserDto user;
    private final NotificationType notificationType;
    private final CommunicationChanel communicationChanel;
}

