package com.novatech.cybertech.factory;

import com.novatech.cybertech.entities.enums.CommunicationType;
import com.novatech.cybertech.listener.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationProcessorStrategyFactory {
    private final Map<CommunicationType, NotificationProcessor> notificationProcessorTypeStrategyMap;

    public NotificationProcessor getStrategy(CommunicationType type) {
        return notificationProcessorTypeStrategyMap.get(type);
    }
}
