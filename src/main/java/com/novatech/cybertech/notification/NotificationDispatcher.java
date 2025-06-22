package com.novatech.cybertech.notification;

import com.novatech.cybertech.entities.NotificationContext;
import com.novatech.cybertech.entities.enums.CommunicationType;
import com.novatech.cybertech.entities.enums.NotificationType;
import com.novatech.cybertech.exceptions.NoStrategyFoundForProcessingTheRequest;
import com.novatech.cybertech.factory.NotificationProcessorStrategyFactory;
import com.novatech.cybertech.factory.NotificationStrategyFactory;
import com.novatech.cybertech.listener.Notification;
import com.novatech.cybertech.listener.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationDispatcher {

    private final NotificationStrategyFactory notificationStrategyFactory;
    private final NotificationProcessorStrategyFactory notificationProcessorStrategyFactory;

    //We can directly use the twop following maps here
    //private final Map<NotificationType, Notification> notificationStrategies;
    //private final Map<CommunicationType, NotificationProcessor> processorStrategies;

    public void dispatch(final NotificationContext context) {
        final Notification notification = notificationStrategyFactory.getStrategy(context.getNotificationType());
        final NotificationProcessor processor = notificationProcessorStrategyFactory.getStrategy(context.getCommunicationType());

        if (notification == null || processor == null) {
            log.error("Aucune stratégie trouvée pour NotificationType={} ou CommunicationType={}", context.getNotificationType(), context.getCommunicationType());
            throw new NoStrategyFoundForProcessingTheRequest("Aucune stratégie trouvée pour NotificationType=" + context.getNotificationType() + " ou CommunicationType=" + context.getCommunicationType());
        }

        // Injection dynamique du processor dans la notification (Bridge)
        //notification.setNotificationProcessor(processor);
        notification.withNotificationProcessor(processor).sendNotification(context);
    }
}
