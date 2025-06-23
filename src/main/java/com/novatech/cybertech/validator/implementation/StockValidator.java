package com.novatech.cybertech.validator.implementation;

import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.entities.OrderItemEntity;
import com.novatech.cybertech.entities.ProductEntity;
import com.novatech.cybertech.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockValidator extends ChainableOrderValidator {

    private final ProductRepository productRepository;

    @Override
    public void validate(OrderEntity order) {
        for (OrderItemEntity item : order.getOrderItemEntities()) {
            ProductEntity product = productRepository.findById(item.getProductEntity().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalStateException("Stock insuffisant pour le produit : " + product.getName());
            }
        }
        nextStep(order);
    }
}
