package com.novatech.cybertech.listener;

import com.novatech.cybertech.annotation.NotificationTypeHandler;
import com.novatech.cybertech.entities.NotificationContext;
import com.novatech.cybertech.entities.NotificationProcessorData;
import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.entities.enums.NotificationType;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@NotificationTypeHandler(NotificationType.ORDER_CONFIRMATION)
public class OrderNotification extends Notification {

    public OrderNotification(NotificationProcessor notificationProcessor) {
        super(notificationProcessor);
    }

    @Override
    public void sendNotification(final NotificationContext notificationContext) {

        OrderEntity savedOrder = (OrderEntity) notificationContext.getPayload();

        final Map<String, Object> model = new HashMap<>();

        model.put("userName", savedOrder.getUserEntity().getFirstName());
        model.put("orderId", savedOrder.getId());
        model.put("amount", savedOrder.getTotalAmount());

        final NotificationProcessorData notificationProcessorData = NotificationProcessorData.builder()
                .processorData(model)
                .build();

        //notificationContext.withNotificationProcessorData(notificationProcessorData);

        notificationProcessor.sendMessage(notificationContext.withNotificationProcessorData(notificationProcessorData));
    }
}
