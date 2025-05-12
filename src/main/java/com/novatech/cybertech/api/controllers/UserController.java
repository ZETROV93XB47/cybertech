package com.novatech.cybertech.api.controllers;

import com.novatech.cybertech.api.error.model.ErrorResponseDto;
import com.novatech.cybertech.dto.request.UserCreateRequestDto;
import com.novatech.cybertech.dto.response.UserResponseDto;
import com.novatech.cybertech.services.implementation.UserServiceImp;
import com.novatech.cybertech.utils.DataGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/services/user")
@Tag(name = "UserController", description = "API for user management") // Ajout d'une description au Tag
public class UserController {

    private final UserServiceImp userService;

    @Operation(summary = "Request a User by UUID",
            description = "Fetches a user's details based on their unique UUID.", // Ajout d'une description plus détaillée
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found successfully", // Description plus précise
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request (e.g., invalid UUID format)",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Operation forbidden",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))})
    @GetMapping(value = "/{userUuid}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDto getUserByUuid(@PathVariable("userUuid") UUID userUuid) {
        return userService.getByUUID(userUuid);
    }


    @Operation(summary = "Create a new User",
            description = "Registers a new user in the system. Email must be unique.",
            // Documentation du corps de la requête
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data for creation. All fields are mandatory except address and birthDate (if configured as optional).",
                    required = true,
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserCreateRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data / Validation error (e.g., email format, missing fields, password too short)",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict - User already exists (e.g., duplicate email)",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error during user creation",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userCreateRequestDto));
    }


    @PostMapping(value = "/create/auto", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserResponseDto>> createUserAutomatically() {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createAutomatically(DataGenerator.generateUsers(100)));
    }


    @Operation(summary = "Health check endpoint",
            description = "A simple endpoint to check if the UserController is responsive.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Service is up and running",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(type = "string", example = "Hello Guys !!! 😁🔥🔥🔥")))
            })
    @GetMapping(value = "/ok", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> healthCheck() {
        return ok("Hello Guys !!! 😁🔥🔥🔥");
    }
}