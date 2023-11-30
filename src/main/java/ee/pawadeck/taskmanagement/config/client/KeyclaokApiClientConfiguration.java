package ee.pawadeck.taskmanagement.config.client;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakClientConfigurationProperties.class)
public class KeyclaokApiClientConfiguration {

    @Bean
    Keycloak keycloak(KeycloakClientConfigurationProperties properties) {
        return KeycloakBuilder.builder()
                .serverUrl(properties.getUrl().toString())
                .realm(properties.getRealm())
                .clientId(properties.getClientId())
                .clientSecret(properties.getClientSecret())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

}
