package com.novatech.cybertech.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message, UUID uuid) {
        super(message);
    }
}
