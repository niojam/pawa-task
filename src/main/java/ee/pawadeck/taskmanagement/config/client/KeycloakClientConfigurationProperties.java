package ee.pawadeck.taskmanagement.config.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URL;

@Data
@Validated
@ConfigurationProperties("keycloak.client")
public class KeycloakClientConfigurationProperties {

    @NotNull
    private URL url;

    @NotBlank
    private String realm;

    @NotBlank
    private String clientId;

    @NotBlank
    private String clientSecret;

}
