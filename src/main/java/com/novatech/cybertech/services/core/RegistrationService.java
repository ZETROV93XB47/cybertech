package com.novatech.cybertech.services.core;

import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.entities.UserEntity;

public interface RegistrationService {
    UserEntity register(final UserCreateRequestDto req);
}
