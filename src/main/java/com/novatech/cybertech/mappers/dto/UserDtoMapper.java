package com.novatech.cybertech.mappers.dto;

import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.dto.response.UserResponseDto;
import com.novatech.cybertech.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends DtoBaseMapper<UserEntity, UserResponseDto, UserCreateRequestDto> {
}
