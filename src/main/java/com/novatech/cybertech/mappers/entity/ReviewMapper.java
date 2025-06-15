package com.novatech.cybertech.mappers.entity;

import com.novatech.cybertech.dto.request.review.ReviewCreateRequestDto;
import com.novatech.cybertech.dto.request.review.ReviewUpdateRequestDto;
import com.novatech.cybertech.dto.response.review.ReviewResponseDto;
import com.novatech.cybertech.entities.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends BaseMapper<ReviewEntity, ReviewCreateRequestDto, ReviewUpdateRequestDto, ReviewResponseDto> {
}
