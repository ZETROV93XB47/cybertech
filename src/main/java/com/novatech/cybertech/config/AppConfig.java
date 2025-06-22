package com.novatech.cybertech.config;

import com.novatech.cybertech.annotation.*;
import com.novatech.cybertech.entities.enums.*;
import com.novatech.cybertech.listener.Notification;
import com.novatech.cybertech.listener.NotificationProcessor;
import com.novatech.cybertech.services.core.PaymentService;
import com.novatech.cybertech.strategy.discount.DiscountStrategy;
import com.novatech.cybertech.strategy.shipping.ShippingStrategy;
import com.novatech.cybertech.validator.core.OrderValidator;
import com.novatech.cybertech.validator.implementation.ActiveUserValidator;
import com.novatech.cybertech.validator.implementation.StockValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final StockValidator stockValidator;
    private final ActiveUserValidator activeUserValidator;

    @Bean
    public OrderValidator orderValidatorChain() {
        activeUserValidator.setNext(stockValidator);
        return activeUserValidator;
    }

    @Bean
    public Map<PaymentType, PaymentService> paymentServiceMap(final ApplicationContext context) {
        final Map<PaymentType, PaymentService> serviceMap = new EnumMap<>(PaymentType.class);

        final Map<String, PaymentService> beans = context.getBeansOfType(PaymentService.class);

        for (PaymentService service : beans.values()) {
            final PaymentTypeHandler annotation = service.getClass().getAnnotation(PaymentTypeHandler.class);
            if (annotation != null) {
                serviceMap.put(annotation.value(), service);
            }
        }

        return serviceMap;
    }


    @Bean
    public Map<ShippingType, ShippingStrategy> shippingStrategyMap(ApplicationContext context) {
        Map<ShippingType, ShippingStrategy> map = new EnumMap<>(ShippingType.class);
        context.getBeansOfType(ShippingStrategy.class).forEach((name, bean) -> {
            ShippingTypeHandler annotation = bean.getClass().getAnnotation(ShippingTypeHandler.class);
            if (annotation != null) {
                map.put(annotation.value(), bean);
            }
        });
        return map;
    }

    @Bean
    public Map<DiscountType, DiscountStrategy> discountStrategyMap(ApplicationContext context) {
        Map<DiscountType, DiscountStrategy> map = new EnumMap<>(DiscountType.class);
        context.getBeansOfType(DiscountStrategy.class).forEach((name, bean) -> {
            DiscountTypeHandler annotation = bean.getClass().getAnnotation(DiscountTypeHandler.class);
            if (annotation != null) {
                map.put(annotation.value(), bean);
            }
        });
        return map;
    }

    @Bean
    public Map<NotificationType, Notification> getNotificationStrategies (final ApplicationContext context) {
        Map<NotificationType, Notification> map = new EnumMap<>(NotificationType.class);
        context.getBeansOfType(Notification.class).forEach((name, bean) -> {
            NotificationTypeHandler annotation = bean.getClass().getAnnotation(NotificationTypeHandler.class);
            if (annotation != null) {
                map.put(annotation.value(), bean);
            }
        });
        return map;
    }

    @Bean
    public Map<CommunicationType, NotificationProcessor> getNotificationProcessorStrategies (final ApplicationContext context) {
        Map<CommunicationType, NotificationProcessor> map = new EnumMap<>(CommunicationType.class);
        context.getBeansOfType(NotificationProcessor.class).forEach((name, bean) -> {
            CommunicationTypeHandler annotation = bean.getClass().getAnnotation(CommunicationTypeHandler.class);
            if (annotation != null) {
                map.put(annotation.value(), bean);
            }
        });
        return map;
    }

}
