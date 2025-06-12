package com.novatech.cybertech.services.core;

import com.novatech.cybertech.dto.request.UserCreateRequestDto;
import com.novatech.cybertech.dto.request.UserUpdateRequestDto;
import com.novatech.cybertech.dto.response.UserResponseDto;

import java.util.UUID;

public interface UserService extends BaseService<UUID, UserCreateRequestDto, UserResponseDto, UserUpdateRequestDto> {
}