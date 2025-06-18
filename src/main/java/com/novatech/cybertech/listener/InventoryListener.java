package com.novatech.cybertech.listener;

import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.entities.OrderItemEntity;
import com.novatech.cybertech.entities.ProductEntity;
import com.novatech.cybertech.events.OrderCreatedEvent;
import com.novatech.cybertech.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final ProductRepository productRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void updateInventory(final OrderCreatedEvent event) {
        OrderEntity order = event.getOrder();

        log.info("[InventoryListener] Mise à jour des stocks pour la commande #{}", order.getId());

        for (OrderItemEntity item : order.getOrderItemEntities()) {
            ProductEntity product = item.getProductEntity();
            int quantityOrdered = item.getQuantity();

            // On décrémente le stock du produit
            product.setStock(product.getStock() - quantityOrdered);

            log.info("Produit: {} | Stock initial: {} | Commandé: {} | Stock final: {}", product.getName(), product.getStock() + quantityOrdered, quantityOrdered, product.getStock());

            productRepository.save(product);
        }
    }
}