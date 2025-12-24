package com.novatech.cybertech.api.controllers;

import com.novatech.cybertech.api.controllers.spec.RegistrationControllerApiSpec;
import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.services.core.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.novatech.cybertech.constants.CyberTechAppConstants.API_BASE_PATH;
import static com.novatech.cybertech.utils.DataGenerator.generateUserCreateRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_BASE_PATH)
public class RegistrationController implements RegistrationControllerApiSpec {

    private final RegistrationService registrationService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<Map<?, ?>> register(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        final UserEntity created = registrationService.register(userCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id", created.getId(),
                "keycloakId", created.getKeycloakId()
        ));
    }

    @PostMapping("/register/auto")
    public ResponseEntity<UserEntity> registerAuto() {

        return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.register(generateUserCreateRequestDto()));
        //        final UserEntity created = registrationService.register(generateUserCreateRequestDto());

//        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
//                "id", created.getId(),
//                "keycloakId", created.getKeycloakId()
//        ));
    }
}
