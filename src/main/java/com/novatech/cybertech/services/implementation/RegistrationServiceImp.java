package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.entities.enums.Role;
import com.novatech.cybertech.repositories.UserRepository;
import com.novatech.cybertech.services.core.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImp implements RegistrationService {

    private final UserRepository userRepository;
    private final KeycloakUserCreationService keycloakUserCreationService;

    @Transactional
    public UserEntity register(final UserCreateRequestDto req) {
        String keycloakId = null;

        try {
            keycloakId = keycloakUserCreationService.createUser(req.getEmail(), req.getFirstName(), req.getLastName(), req.getPassword());

            UserEntity user = UserEntity.builder()
                    .email(req.getEmail())
                    .firstName(req.getFirstName())
                    .lastName(req.getLastName())
                    .sex(req.getSex())
                    .address(req.getAddress())
                    .birthDate(req.getBirthDate())
                    .phoneNumber(req.getPhoneNumber())
                    .favoriteCommunicationChanel(req.getFavoriteCommunicationChanel())
                    .role(Role.USER)
                    .keycloakId(keycloakId)
                    .isActive(true)
                    .build();

            return userRepository.save(user);

        }

        catch (RuntimeException e) {
            if (keycloakId != null) {
                keycloakUserCreationService.deleteUser(keycloakId);
            }
            throw e;
        }
    }
}
