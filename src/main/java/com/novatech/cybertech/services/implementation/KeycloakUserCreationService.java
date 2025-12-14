package com.novatech.cybertech.services.implementation;

import com.novatech.cybertech.config.KeycloakAdminClientConfig;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakUserCreationService {

    private final Keycloak keycloak;
    private final KeycloakAdminClientConfig keycloakAdminClientConfig;

    public String createUser(String email, String firstName, String lastName, String rawPassword) {

        final UsersResource users = keycloak.realm(keycloakAdminClientConfig.getRealm()).users();

        UserRepresentation userRepresentation = getUserRepresentation(email, firstName, lastName, rawPassword);

        final Response resp = users.create(userRepresentation);

        if (resp.getStatus() != 201) {
            String msg = resp.readEntity(String.class);
            throw new IllegalStateException("Keycloak user creation failed: " + resp.getStatus() + " " + msg);
        }

        // L’ID est dans le Location header: .../users/{id}
        String location = resp.getLocation().toString();
        return location.substring(location.lastIndexOf('/') + 1);
    }

    private UserRepresentation getUserRepresentation(String email, String firstName, String lastName, String rawPassword) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(createUserNameFromFirstNameAndLastName(firstName, lastName, email));
        userRepresentation.setEmail(email);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setTemporary(false);
        cred.setValue(rawPassword);
        userRepresentation.setCredentials(List.of(cred));
        return userRepresentation;
    }

    public void deleteUser(String keycloakUserId) {
        keycloak.realm(keycloakAdminClientConfig.getRealm()).users().delete(keycloakUserId);
    }

    private String createUserNameFromFirstNameAndLastName(final String firstName, final String lastName, final String email) {
        if ((!StringUtils.isEmpty(firstName)) && (!StringUtils.isEmpty(lastName))) {
            return firstName.trim().toLowerCase() + "." + lastName.trim().toLowerCase();
        }
        return email.split("@")[0];
    }
}
