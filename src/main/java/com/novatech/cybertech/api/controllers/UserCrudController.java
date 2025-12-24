package com.novatech.cybertech.api.controllers;

import com.novatech.cybertech.api.controllers.spec.UserCrudControllerApiSpec;
import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.dto.request.user.UserUpdateRequestDto;
import com.novatech.cybertech.dto.response.user.UserResponseDto;
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

import static com.novatech.cybertech.constants.CyberTechAppConstants.USER_CRUD_CONTROLLER_BASE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequiredArgsConstructor
@RequestMapping(USER_CRUD_CONTROLLER_BASE_PATH)
@Tag(name = "UserController", description = "API for user management")
public class UserCrudController implements UserCrudControllerApiSpec {

    private final UserServiceImp userService;

    @Override
    @GetMapping(value = "/get/{userUuid}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getUserByUuid(@PathVariable("userUuid") UUID userUuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByUUID(userUuid));
    }

    @Override
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userCreateRequestDto));
    }

    @Override
    @PatchMapping(value = "/update/{userUuid}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> updateUser(final UserUpdateRequestDto userUpdateRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userUpdateRequestDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{userUuid}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUserByUuid(UUID userUuid) {
        userService.deleteByUUID(userUuid);
        return ResponseEntity.noContent().build();
    }


    //@PreAuthorize("hasRole('ADMIN')")
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