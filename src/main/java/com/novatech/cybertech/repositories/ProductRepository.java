package com.novatech.cybertech.repositories;

import com.novatech.cybertech.entities.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudBaseRepository<ProductEntity, Long> {
}
