package com.javatechie.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;

import static com.javatechie.constants.KafkaTopicNames.TOPIC_NAME_CUSTOMER_DATA;

public class KafkaEventBeanConfig {

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(TOPIC_NAME_CUSTOMER_DATA, 3, (short) 1);
    }
}
