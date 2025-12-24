package com.novatech.cybertech.repositories;

import com.novatech.cybertech.entities.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudBaseRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByUuid(final UUID userUuid);
}
