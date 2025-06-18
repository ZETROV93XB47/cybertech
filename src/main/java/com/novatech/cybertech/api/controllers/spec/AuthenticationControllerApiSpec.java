package com.novatech.cybertech.api.controllers.spec;

import com.novatech.cybertech.api.error.model.ErrorResponseDto;
import com.novatech.cybertech.dto.request.auth.UserLoginRequestDto;
import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.dto.response.auth.JwtResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;


@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public interface AuthenticationControllerApiSpec {

    @Operation(summary = "User Login",
            description = "Authenticates a user and returns a JWT token upon successful login.",
            requestBody = @RequestBody(
                    description = "User credentials for login.",
                    required = true,
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserLoginRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data / Validation error",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    ResponseEntity<JwtResponseDto> login(final UserLoginRequestDto req);

    @Operation(summary = "User Registration",
            description = "Registers a new user in the system.",
            requestBody = @RequestBody(
                    description = "User data for registration.",
                    required = true,
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserCreateRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE, schema = @Schema(type = "string", example = "User registered successfully with UUID: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data / Validation error (e.g., email format, missing fields, password too short)",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict - User already exists (e.g., duplicate email)",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    ResponseEntity<String> register(final UserCreateRequestDto req);
}
