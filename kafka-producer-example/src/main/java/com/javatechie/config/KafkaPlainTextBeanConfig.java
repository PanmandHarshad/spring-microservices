package com.javatechie.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.javatechie.constants.KafkaTopicNames.TOPIC_NAME_TEXT_DATA;

@Configuration
public class KafkaPlainTextBeanConfig {

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(TOPIC_NAME_TEXT_DATA, 3, (short) 1);
    }
}
