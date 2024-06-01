package com.javatechie.consumer;

import com.javatechie.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

//    /**
//     * Here need to consume the message of type String, as producer is producing the message of type String
//     *
//     * @param message consumed message
//     */
//    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group-new")
//    public void consume1(String message) {
//        logger.info("Consumer1 consume the message {}", message);
//    }
//
//    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group-new")
//    public void consume2(String message) {
//        logger.info("Consumer2 consume the message {}", message);
//    }
//
//    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group-new")
//    public void consume3(String message) {
//        logger.info("Consumer3 consume the message {}", message);
//    }
//
//    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group-new")
//    public void consume4(String message) {
//        logger.info("Consumer4 consume the message {}", message);
//    }

    /**
     * Here need to consume the event of type Customer, as producer is producing the events of type Customer
     *
     * @param customer consumed customer
     */
    @KafkaListener(topics = "javatechie-customer-1", groupId = "jt-group")
    public void consumeEvents(Customer customer) {
        logger.info("Consumer1 consume the events {}", customer.toString());
    }
}
