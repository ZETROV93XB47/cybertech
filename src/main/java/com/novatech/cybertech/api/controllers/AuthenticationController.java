package com.novatech.cybertech.api.controllers;

import com.novatech.cybertech.api.controllers.spec.AuthenticationControllerApiSpec;
import com.novatech.cybertech.data.CustomUserDetails;
import com.novatech.cybertech.services.core.JwtService;
import com.novatech.cybertech.dto.request.auth.UserLoginRequestDto;
import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.dto.response.auth.JwtResponseDto;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.entities.enums.Role;
import com.novatech.cybertech.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.novatech.cybertech.constants.CyberTechAppConstants.AUTHENTICATION_CONTROLLER_BASE_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_CONTROLLER_BASE_PATH)
public class AuthenticationController implements AuthenticationControllerApiSpec {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        final UserEntity user = UserEntity.builder()
                .email(userCreateRequestDto.getEmail())
                .password(passwordEncoder.encode(userCreateRequestDto.getPassword()))
                .firstName(userCreateRequestDto.getFirstName())
                .lastName(userCreateRequestDto.getLastName())
                .role(Role.USER)
                .sex(userCreateRequestDto.getSex())
                .isActive(true)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        final UserEntity user = userRepository.findByEmail(userLoginRequestDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Username Not found"));

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) throw new BadCredentialsException("Invalid password");

        final String token = jwtService.generateToken(new CustomUserDetails(user));

        return ResponseEntity.ok(new JwtResponseDto(token));
    }
}
