package ee.pawadeck.taskmanagement.config.kafka;

import ee.pawadeck.tasks.TaskEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaCustomProperties.class)
public class KafkaProducerConfiguration {

    private final KafkaProperties kafkaProperties;
    private final KafkaCustomProperties kafkaCustomProperties;

    @Bean
    public KafkaTemplate<String, TaskEvent> addressEventKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public <T> ProducerFactory<String, T> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerDefaultProperties());
    }

    private Map<String, Object> producerDefaultProperties() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        properties.putAll(kafkaCustomProperties.buildCommonProperties());
        return properties;
    }

}
