package com.novatech.cybertech.repositories;

import com.novatech.cybertech.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends BaseRepository<ReviewEntity, Long> {
}
