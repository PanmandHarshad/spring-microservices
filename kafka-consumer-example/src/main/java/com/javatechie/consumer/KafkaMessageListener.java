package com.javatechie.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    /**
     * Here need to consume the message of type String, as producer is producing the message of type String
     *
     * @param message consumed message
     */
    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group-new")
    public void consume1(String message) {
        logger.info("Consumer1 consume the message {}", message);
    }

    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group-new")
    public void consume2(String message) {
        logger.info("Consumer2 consume the message {}", message);
    }

    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group-new")
    public void consume3(String message) {
        logger.info("Consumer3 consume the message {}", message);
    }

    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group-new")
    public void consume4(String message) {
        logger.info("Consumer4 consume the message {}", message);
    }
}
