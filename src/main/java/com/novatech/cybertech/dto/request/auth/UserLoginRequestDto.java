package com.novatech.cybertech.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequestDto {

    @NotBlank(message = "Email field cannot be empty")
    @Email(message = "The email format is not valid")
    private String email;

    @NotBlank(message = "Password field cannot be empty")
    private String password;
}