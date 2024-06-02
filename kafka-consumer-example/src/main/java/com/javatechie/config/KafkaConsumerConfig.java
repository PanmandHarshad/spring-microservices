package com.javatechie.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka consumer settings.
 *
 * <p>This configuration is done within a Java class instead of an application.yml file
 * for several reasons:</p>
 *
 * <ul>
 *     <li><b>Flexibility and Control:</b> Using a Java configuration class gives more control and flexibility
 *     over the configuration process. It allows adding logic to the configuration methods, which is not possible
 *     with the static nature of application.yml.</li>
 *     <li><b>Type Safety:</b> Java configuration provides compile-time type safety. Any issues with the configuration
 *     setup (e.g., wrong class types for deserializers) can be caught during compilation rather than at runtime.</li>
 *     <li><b>Complex Configuration:</b> Some configurations might involve complex logic or condition-based setup
 *     that is easier to express in Java code than in YAML.</li>
 *     <li><b>Centralized Configuration:</b> Having all Kafka configurations centralized in one Java class can be more
 *     convenient, keeping all related configurations together and easily accessible.</li>
 *     <li><b>Library or Framework Constraints:</b> Some libraries or frameworks might not fully support all features
 *     via application.yml and might require Java configuration. Using Java configuration ensures compatibility with
 *     all features provided by Spring Kafka.</li>
 *     <li><b>Easier Debugging:</b> Java configurations can be debugged using traditional Java debugging tools.
 *     If there's an issue with the configuration, you can set breakpoints and inspect variables during the
 *     initialization process.</li>
 *     <li><b>Consistency with Other Java-Based Configurations:</b> Often in a Java-based application, multiple
 *     configurations are managed through Java classes. Keeping Kafka configuration in a Java class maintains
 *     consistency with the rest of the application's configuration approach.</li>
 * </ul>
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * Configures the properties for the Kafka consumer, including bootstrap servers and deserializers.
     *
     * @return a Map containing the consumer configuration properties.
     */
    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.javatechie.dto");
        return props;
    }

    /**
     * Creates a ConsumerFactory that sets up the configuration for Kafka consumer instances.
     *
     * @return the ConsumerFactory configured with consumer properties.
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    /**
     * Creates a KafkaListenerContainerFactory for setting up concurrent Kafka message listeners.
     * It uses the ConsumerFactory for configuration.
     *
     * @return the KafkaListenerContainerFactory configured for creating ConcurrentMessageListenerContainer instances.
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
