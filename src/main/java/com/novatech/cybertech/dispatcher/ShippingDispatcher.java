package com.novatech.cybertech.dispatcher;

import com.novatech.cybertech.data.ShippingContext;
import com.novatech.cybertech.exceptions.NoStrategyFoundForProcessingTheRequest;
import com.novatech.cybertech.factory.ShippingProviderStrategyFactory;
import com.novatech.cybertech.factory.ShippingTypeProcessorStrategyFactory;
import com.novatech.cybertech.services.core.AbstractShippingProvider;
import com.novatech.cybertech.services.core.ShippingStrategyProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingDispatcher {

    private final ShippingProviderStrategyFactory shippingProviderStrategyFactory;
    private final ShippingTypeProcessorStrategyFactory shippingTypeProcessorStrategyFactory;

    public void dispatch(final ShippingContext context) {
        final AbstractShippingProvider shippingProvider = shippingProviderStrategyFactory.getStrategy(context.getShippingProvider());
        final ShippingStrategyProcessor shippingStrategyProcessor = shippingTypeProcessorStrategyFactory.getStrategy(context.getShippingType());

        if (shippingProvider == null || shippingStrategyProcessor == null) {
            log.error("Aucune stratégie trouvée pour ShippingType={} ou ShippingProvider={}", context.getShippingType(), context.getShippingProvider());
            throw new NoStrategyFoundForProcessingTheRequest("Aucune stratégie trouvée pour ShippingType=" + context.getShippingType() + " ou ShippingProvider = " + context.getShippingProvider());
        }

        shippingProvider.withShippingStrategyProcessor(shippingStrategyProcessor).ship(context);
    }
}
