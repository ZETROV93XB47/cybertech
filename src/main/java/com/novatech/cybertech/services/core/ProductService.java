package com.novatech.cybertech.services.core;

import com.novatech.cybertech.dto.request.product.ProductCreateRequestDto;
import com.novatech.cybertech.dto.request.product.ProductUpdateRequestDto;
import com.novatech.cybertech.dto.response.product.ProductResponseDto;

import java.util.UUID;

//TODO: à refactorer plus tard
public interface ProductService extends CrudBaseService<UUID, ProductCreateRequestDto, ProductUpdateRequestDto, ProductResponseDto> {
}