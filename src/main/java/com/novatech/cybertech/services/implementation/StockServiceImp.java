package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.entities.ProductEntity;
import com.novatech.cybertech.entities.StockEntity;
import com.novatech.cybertech.exceptions.NotEnoughStockException;
import com.novatech.cybertech.exceptions.ProductNotFoundException;
import com.novatech.cybertech.repositories.ProductRepository;
import com.novatech.cybertech.repositories.StockRepository;
import com.novatech.cybertech.services.core.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.novatech.cybertech.entities.enums.ReservationStatus.ACTIVE;


@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImp implements StockService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration RESERVATION_TTL = Duration.ofMinutes(10);


    @Override
    @Transactional
    public void reserveStock(UUID orderUuid, Map<UUID, Integer> quantities) {
        quantities.entrySet().forEach(entry -> lockAndReserveProduct(orderUuid,entry));
        // 3️⃣ Redis TTL (cache + expiration)
        redisTemplate.opsForValue().set("reservation:order:" + orderUuid, "ACTIVE", RESERVATION_TTL);
    }


    @Override
    @Transactional
    public void commitStock(UUID orderUuid) {

        List<StockEntity> reservations = stockRepository.findByOrderUuid(orderUuid);

        reservations.stream().map(this::updateStockForCommit).forEach(productRepository::save);

        stockRepository.deleteByOrderUuid(orderUuid);

        redisTemplate.delete("reservation:order:" + orderUuid);
    }

    @Override
    @Transactional
    public void releaseStock(UUID orderUuid) {
        List<StockEntity> reservations = stockRepository.findByOrderUuid(orderUuid);

        if(reservations.isEmpty()) return;

        reservations.forEach(this::updateStockForRelease);

        stockRepository.deleteByOrderUuid(orderUuid);
        redisTemplate.delete("reservation:order:" + orderUuid);
    }




    private void updateStockForRelease(StockEntity r) {
        ProductEntity product = productRepository.lockByUuid(r.getProductUuid()).orElseThrow();
        product.setReservedStock(product.getReservedStock() - r.getQuantity());
        productRepository.save(product);
    }

    private ProductEntity updateStockForCommit(StockEntity r) {
        ProductEntity product = productRepository.lockByUuid(r.getProductUuid()).orElseThrow(() -> new ProductNotFoundException("No product with the UUID : " + r.getProductUuid() + " found"));
        product.setReservedStock(product.getReservedStock() - r.getQuantity());
        product.setStock(product.getStock() - r.getQuantity());
        return product;
    }

    private void lockAndReserveProduct(UUID orderUuid, Map.Entry<UUID, Integer> entry) {
        UUID productUuid = entry.getKey();
        int qty = entry.getValue();

        ProductEntity product = productRepository.lockByUuid(productUuid).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        int available = product.getStock() - product.getReservedStock();
        if (available < qty) {
            throw new NotEnoughStockException("Not enough stock");
        }

        // 1️⃣ réserver en DB
        StockEntity reservation = StockEntity.builder()
                .orderUuid(orderUuid)
                .productUuid(productUuid)
                .reservationStatus(ACTIVE)
                .quantity(qty)
                .build();

        stockRepository.save(reservation);

        // 2️⃣ incrémenter l’agrégat
        product.setReservedStock(product.getReservedStock() + qty);
        productRepository.save(product);
    }

}




