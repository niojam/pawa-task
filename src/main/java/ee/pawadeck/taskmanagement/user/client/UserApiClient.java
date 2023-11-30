package ee.pawadeck.taskmanagement.user.client;

import ee.pawadeck.taskmanagement.config.client.KeycloakClientConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserApiClient {

    private final Keycloak keycloak;
    private final KeycloakClientConfigurationProperties keycloakClientConfigurationProperties;

    public UserRepresentation getUserInfoById(UUID id) {
        return keycloak.realm(keycloakClientConfigurationProperties.getRealm())
                .users()
                .get(id.toString())
                .toRepresentation();
    }

}
