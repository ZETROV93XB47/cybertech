package com.novatech.cybertech.dto.request.order;

import com.novatech.cybertech.dto.request.orderItem.OrderItemCreateRequestDto;
import com.novatech.cybertech.entities.enums.OrderStatus;
import com.novatech.cybertech.entities.enums.PaymentType;
import com.novatech.cybertech.entities.enums.ShippingType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderPlacingRequestDto {

    @NotNull(message = "User UUID cannot be null")
    private UUID userUuid;

    @NotNull(message = "Status cannot be null")
    private OrderStatus status;

    @NotNull(message = "Shipping Address cannot be null")
    private String shippingAddress;

    @NotNull(message = "Shipping Type cannot be null")
    private ShippingType shippingType;

    @NotNull(message = "The payment Method Type cannot be null")
    private PaymentType paymentMethodType;

    @NotNull(message = "Order Payment Request Dto cannot be null and must be provided for payment")
    private OrderPaymentRequestDto orderPaymentRequestDto;

    @NotNull(message = "Cannot create an order without items in the order")
    private List<OrderItemCreateRequestDto> orderItems;
}

//Order date will be updated in the service layer
//    @NotNull(message = "Cart UUID cannot be null for order creation")
//    private UUID cartUuid; // Assumes an order is created from an existing cart
