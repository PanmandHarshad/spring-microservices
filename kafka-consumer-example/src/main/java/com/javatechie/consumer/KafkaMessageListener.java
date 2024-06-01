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
    @KafkaListener(topics = "javatechie-demo", groupId = "jt-group-1")
    public void consume(String message) {
        logger.info("Consumer consume the message {}", message);
    }
}
