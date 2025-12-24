package com.novatech.cybertech.dto.response.auth;

import lombok.Builder;

@Builder
public record JwtResponseDto(String token) {
}
