package ee.pawadeck.taskmanagement.config.kafka;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static org.apache.kafka.common.security.auth.SecurityProtocol.SASL_PLAINTEXT;
import static org.apache.kafka.common.security.auth.SecurityProtocol.SASL_SSL;

@Data
@Validated
@ConfigurationProperties("kafka")
public class KafkaCustomProperties {

    @NotNull
    private final URI schemaRegistryUrl;

    private final boolean autoRegisterSchemas;

    private final Sasl sasl;

    @NotNull
    private final Security security;

    @Getter
    @Validated
    @RequiredArgsConstructor
    public static class Sasl {

        private final String mechanism;

        @NotEmpty
        private final String jaasConfig;

    }

    @Getter
    @Validated
    @RequiredArgsConstructor
    public static class Security {

        @NotEmpty
        private final String protocol;

    }

    public Map<String, Object> buildCommonProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl.toString());
        properties.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, autoRegisterSchemas);
        properties.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, TRUE);
        properties.put(AbstractKafkaSchemaSerDeConfig.NORMALIZE_SCHEMAS, TRUE);
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, TRUE);
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, security.getProtocol());

        if (List.of(SASL_PLAINTEXT.name(), SASL_SSL.name()).contains(security.getProtocol())) {
            properties.put(SaslConfigs.SASL_MECHANISM, sasl.getMechanism());
            properties.put(SaslConfigs.SASL_JAAS_CONFIG, sasl.getJaasConfig());
        }
        return properties;
    }

}
