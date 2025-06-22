package com.novatech.cybertech.listener;

import com.novatech.cybertech.entities.NotificationContext;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.entities.enums.NotificationType;
import com.novatech.cybertech.events.OrderCreatedEvent;
import com.novatech.cybertech.notification.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEvenListener {

    private final NotificationDispatcher notificationDispatcher;

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        final UserEntity user = event.getOrder().getUserEntity();

        final NotificationContext context = NotificationContext.builder()
                .notificationType(NotificationType.ORDER_CONFIRMATION)
                .communicationType(user.getFavoriteCommunicationChanel()) // ex: EMAIL
                .user(user)
                .payload(event.getOrder())
                .message("Votre commande #" + event.getOrder().getId() + " a bien été confirmée.")
                .build();

        notificationDispatcher.dispatch(context);
    }
}
