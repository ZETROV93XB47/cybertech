package com.novatech.cybertech.repositories;

import com.novatech.cybertech.entities.PaymentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudBaseRepository<PaymentEntity, Long> {
}
