package com.novatech.cybertech.listener;

import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.events.OrderCreatedEvent;
import com.novatech.cybertech.services.implementation.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationListener {

    private final MailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notifyCustomer(final OrderCreatedEvent event) {
        final OrderEntity savedOrder = event.getOrder();
        final Map<String, Object> model = new HashMap<>();

        model.put("userName", savedOrder.getUserEntity().getFirstName());
        model.put("orderId", savedOrder.getId());
        model.put("amount", savedOrder.getTotalAmount());

        emailService.sendOrderConfirmationEmail(savedOrder.getUserEntity().getEmail(), model);
    }
}
