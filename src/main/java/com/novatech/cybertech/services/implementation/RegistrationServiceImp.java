package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.dto.request.user.UserCreateRequestDto;
import com.novatech.cybertech.entities.BankCardEntity;
import com.novatech.cybertech.entities.UserEntity;
import com.novatech.cybertech.entities.enums.Role;
import com.novatech.cybertech.repositories.BankCardRepository;
import com.novatech.cybertech.repositories.UserRepository;
import com.novatech.cybertech.services.core.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImp implements RegistrationService {

    private final UserRepository userRepository;
    private final BankCardRepository bankCardRepository;
    private final KeycloakUserCreationService keycloakUserCreationService;

    @Transactional
    public UserEntity register(final UserCreateRequestDto req) {
        String keycloakId = null;

        try {
            keycloakId = keycloakUserCreationService.createUser(req.getEmail(), req.getFirstName(), req.getLastName(), req.getPassword());

            UserEntity user = UserEntity.builder()
                    .uuid(UUID.randomUUID())
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

            UserEntity savedUser = userRepository.save(user);

            BankCardEntity bankCard = BankCardEntity.builder()
                    .cardHolderName(req.getBankCardCreationRequestDto().getCardHolderName())
                    .cardNumber(req.getBankCardCreationRequestDto().getCardNumber())
                    .expiryDate(req.getBankCardCreationRequestDto().getExpiryDate())
                    .cardType(req.getBankCardCreationRequestDto().getCardType())
                    .isDefault(true)
                    .build();

            bankCard.setUserEntity(savedUser);
            bankCard.setUuid(UUID.randomUUID());

            bankCardRepository.save(bankCard);

            return savedUser;

        } catch (RuntimeException e) {
            if (keycloakId != null) {
                keycloakUserCreationService.deleteUser(keycloakId);
            }
            throw e;
        }
    }
}
