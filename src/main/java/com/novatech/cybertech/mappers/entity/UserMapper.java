package com.novatech.cybertech.mappers.entity;

import com.novatech.cybertech.dto.request.UserCreateRequestDto;
import com.novatech.cybertech.dto.response.UserResponseDto;
import com.novatech.cybertech.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserEntity, UserCreateRequestDto, UserResponseDto> {
}
