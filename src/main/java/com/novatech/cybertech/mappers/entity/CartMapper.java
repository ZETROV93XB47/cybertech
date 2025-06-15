package com.novatech.cybertech.mappers.entity;

import com.novatech.cybertech.dto.request.cart.CartCreateRequestDto;
import com.novatech.cybertech.dto.request.cart.CartUpdateRequestDto;
import com.novatech.cybertech.dto.response.cart.CartResponseDto;
import com.novatech.cybertech.entities.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper extends BaseMapper<CartEntity, CartCreateRequestDto, CartUpdateRequestDto, CartResponseDto> {
}
