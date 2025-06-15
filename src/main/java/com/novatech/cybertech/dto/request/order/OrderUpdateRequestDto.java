package com.novatech.cybertech.dto.request.order;

import com.novatech.cybertech.entities.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequestDto {

    @NotNull(message = "Order UUID cannot be null")
    private UUID orderUuid;

    @NotNull(message = "Cart UUID cannot be null for order creation")
    private UUID cartUuid; // Assumes an order is created from an existing cart

    @NotNull(message = "Status cannot be null")
    private OrderStatus status;

    @Size(max = 255, message = "Shipping address must be at most 255 characters")
    private String shippingAddress;

}
