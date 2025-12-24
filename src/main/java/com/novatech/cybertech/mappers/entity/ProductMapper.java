package com.novatech.cybertech.mappers.entity;

import com.novatech.cybertech.dto.request.product.ProductCreateRequestDto;
import com.novatech.cybertech.dto.request.product.ProductUpdateRequestDto;
import com.novatech.cybertech.dto.response.product.ProductResponseDto;
import com.novatech.cybertech.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<ProductEntity, ProductCreateRequestDto, ProductUpdateRequestDto, ProductResponseDto> {
}
