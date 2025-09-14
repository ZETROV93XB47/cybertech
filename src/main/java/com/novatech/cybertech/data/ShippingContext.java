package com.novatech.cybertech.data;

import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.entities.enums.ShippingProvider;
import com.novatech.cybertech.entities.enums.ShippingType;
import lombok.*;

@With
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ShippingContext {
    private final Object payload;
    private final UserEntity user;
    private final ShippingType shippingType;
    private final ShippingProvider shippingProvider;
}
