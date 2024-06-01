package com.javatechie.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka producer settings.
 *
 * <p>This configuration is done within a Java class instead of an application.yml file
 * for several reasons:</p>
 *
 * <ul>
 *     <li><b>Flexibility and Control:</b> Using a Java configuration class gives more control and flexibility
 *     over the configuration process. It allows adding logic to the configuration methods, which is not possible
 *     with the static nature of application.yml.</li>
 *     <li><b>Type Safety:</b> Java configuration provides compile-time type safety. Any issues with the configuration
 *     setup (e.g., wrong class types for serializers) can be caught during compilation rather than at runtime.</li>
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
public class KafkaProducerConfig {

    /**
     * Creates a new Kafka topic named "javatechie-customer-1" with 3 partitions and a replication factor of 1.
     *
     * @return the NewTopic object representing the created topic.
     */
    @Bean
    public NewTopic createTopic() {
        return new NewTopic("javatechie-customer-1", 3, (short) 1);
    }
//
//    /**
//     * Configures the properties for the Kafka producer, including bootstrap servers and serializers.
//     *
//     * @return a Map containing the producer configuration properties.
//     */
//    @Bean
//    public Map<String, Object> producerConfig() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return props;
//    }
//
//    /**
//     * Creates a ProducerFactory that sets up the configuration for Kafka producer instances.
//     *
//     * @return the ProducerFactory configured with producer properties.
//     */
//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfig());
//    }
//
//    /**
//     * Creates a KafkaTemplate for sending messages to Kafka. It uses the ProducerFactory for configuration.
//     *
//     * @return the KafkaTemplate configured for producing messages.
//     */
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
}
