package com.novatech.cybertech.repositories;

import com.novatech.cybertech.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends BaseRepository<CartEntity, Long> {
}