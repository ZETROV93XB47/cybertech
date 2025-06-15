package com.novatech.cybertech.services.implementation;


import com.novatech.cybertech.dto.request.review.ReviewCreateRequestDto;
import com.novatech.cybertech.dto.request.review.ReviewUpdateRequestDto;
import com.novatech.cybertech.dto.response.review.ReviewResponseDto;
import com.novatech.cybertech.entities.ReviewEntity;
import com.novatech.cybertech.exceptions.ReviewNotFoundException;
import com.novatech.cybertech.mappers.entity.ReviewMapper;
import com.novatech.cybertech.repositories.ReviewRepository;
import com.novatech.cybertech.services.core.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService {

    private final ReviewMapper productMapper;
    private final ReviewRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<ReviewResponseDto> getAll() {
        return productMapper.mapFromEntityToDto(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDto getByUUID(UUID uuid) {
        return productMapper.mapFromEntityToDto(productRepository.findByUuid(uuid).orElseThrow(() -> new ReviewNotFoundException("No product with the UUID : " + uuid + " found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ReviewResponseDto> getByUUIDs(Collection<UUID> uuids) {
        return productMapper.mapFromEntityToDto(productRepository.findAllByUuidIn(uuids));
    }

    @Override
    @Transactional
    public ReviewResponseDto create(ReviewCreateRequestDto productCreateRequestDto) {
        return productMapper.mapFromEntityToDto(productRepository.save(productMapper.mapFromCreationRequestToEntity(productCreateRequestDto)));
    }

    @Transactional
    public Collection<ReviewResponseDto> createAutomatically(Collection<ReviewEntity> products) {
        return new ArrayList<>(productMapper.mapFromEntityToDto(products.stream().map(productRepository::save).toList()));
    }

    @Override
    @Transactional
    public ReviewResponseDto update(final ReviewUpdateRequestDto productCreateRequestDto) {
        return productMapper.mapFromEntityToDto(productRepository.save(productMapper.mapFromUpdateRequestToEntity(productCreateRequestDto)));
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID uuid) {
        productRepository.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public void deleteByUUIDs(Collection<UUID> uuids) {
        productRepository.deleteAllByUuidIn(uuids);
    }
}
