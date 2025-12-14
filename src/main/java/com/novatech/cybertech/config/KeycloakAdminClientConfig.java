package com.novatech.cybertech.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak-user-management")
public class KeycloakAdminClientConfig {
    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
}
