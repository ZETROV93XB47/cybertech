package com.novatech.cybertech.services.core;

import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.dto.request.user.UserUpdateRequestDto;
import com.novatech.cybertech.dto.response.UserResponseDto;

import java.util.UUID;

public interface UserService extends BaseService<UUID, UserCreateRequestDto, UserResponseDto, UserUpdateRequestDto> {
}