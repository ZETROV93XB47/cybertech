package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.dto.request.cart.CartCreateRequestDto;
import com.novatech.cybertech.dto.request.cart.CartUpdateRequestDto;
import com.novatech.cybertech.dto.response.cart.CartResponseDto;
import com.novatech.cybertech.entities.CartEntity;
import com.novatech.cybertech.exceptions.CartNotFoundException;
import com.novatech.cybertech.mappers.entity.CartMapper;
import com.novatech.cybertech.repositories.CartRepository;
import com.novatech.cybertech.services.core.CartService;
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
public class CartServiceImp implements CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<CartResponseDto> getAll() {
        return cartMapper.mapFromEntityToDto(cartRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDto getByUUID(UUID uuid) {
        return cartMapper.mapFromEntityToDto(cartRepository.findByUuid(uuid).orElseThrow(() -> new CartNotFoundException("No cart with the UUID : " + uuid + " found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CartResponseDto> getByUUIDs(Collection<UUID> uuids) {
        return cartMapper.mapFromEntityToDto(cartRepository.findAllByUuidIn(uuids));
    }

    @Override
    @Transactional
    public CartResponseDto create(CartCreateRequestDto cartCreateRequestDto) {
        return cartMapper.mapFromEntityToDto(cartRepository.save(cartMapper.mapFromCreationRequestToEntity(cartCreateRequestDto)));
    }

    @Transactional
    public Collection<CartResponseDto> createAutomatically(Collection<CartEntity> carts) {
        return new ArrayList<>(cartMapper.mapFromEntityToDto(carts.stream().map(cartRepository::save).toList()));
    }

    @Override
    @Transactional
    public CartResponseDto update(final CartUpdateRequestDto cartCreateRequestDto) {
        return cartMapper.mapFromEntityToDto(cartRepository.save(cartMapper.mapFromUpdateRequestToEntity(cartCreateRequestDto)));
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID uuid) {
        cartRepository.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public void deleteByUUIDs(Collection<UUID> uuids) {
        cartRepository.deleteAllByUuidIn(uuids);
    }
}
