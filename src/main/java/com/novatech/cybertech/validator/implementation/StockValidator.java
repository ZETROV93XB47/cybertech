package com.novatech.cybertech.validator.implementation;

import com.novatech.cybertech.entities.BaseEntity;
import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.entities.OrderItemEntity;
import com.novatech.cybertech.entities.ProductEntity;
import com.novatech.cybertech.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockValidator extends ChainableOrderValidator {

    private final ProductRepository productRepository;

    @Override
    public void validate(final BaseEntity entity) {
        final OrderEntity order = (OrderEntity) entity;

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
