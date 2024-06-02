package com.javatechie.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.javatechie.constants.KafkaConsumerConstants.GROUP_ID_CONSUMER_TEXT_DATA;
import static com.javatechie.constants.KafkaConsumerConstants.TOPIC_NAME_TEXT_DATA;

@Service
public class KafkaMessageListenerPlainText {

    Logger logger = LoggerFactory.getLogger(KafkaMessageListenerPlainText.class);

    /**
     * Here need to consume the message of type String, as producer is producing the message of type String
     *
     * @param message consumed message
     */
    @KafkaListener(topics = TOPIC_NAME_TEXT_DATA, groupId = GROUP_ID_CONSUMER_TEXT_DATA)
    public void consume1(String message) {
        logger.info("Consumer1 consume the message {}", message);
    }

    @KafkaListener(topics = TOPIC_NAME_TEXT_DATA, groupId = GROUP_ID_CONSUMER_TEXT_DATA)
    public void consume2(String message) {
        logger.info("Consumer2 consume the message {}", message);
    }

    @KafkaListener(topics = TOPIC_NAME_TEXT_DATA, groupId = GROUP_ID_CONSUMER_TEXT_DATA)
    public void consume3(String message) {
        logger.info("Consumer3 consume the message {}", message);
    }

    @KafkaListener(topics = TOPIC_NAME_TEXT_DATA, groupId = GROUP_ID_CONSUMER_TEXT_DATA)
    public void consume4(String message) {
        logger.info("Consumer4 consume the message {}", message);
    }
}