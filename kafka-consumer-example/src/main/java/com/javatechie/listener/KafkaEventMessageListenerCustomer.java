package com.javatechie.listener;

import com.javatechie.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.javatechie.constants.KafkaConsumerConstants.GROUP_ID_CONSUMER_CUSTOMER;
import static com.javatechie.constants.KafkaConsumerConstants.TOPIC_NAME_CUSTOMER_DATA;

@Service
public class KafkaEventMessageListenerCustomer {

    Logger logger = LoggerFactory.getLogger(KafkaEventMessageListenerCustomer.class);

    /**
     * Here need to consume the event of type Customer, as producer is producing the events of type Customer
     *
     * @param customer consumed customer
     */
    @KafkaListener(topics = TOPIC_NAME_CUSTOMER_DATA, groupId = GROUP_ID_CONSUMER_CUSTOMER)
    public void consumeEvents(Customer customer) {
        logger.info("Consumer1 consume the events {}", customer.toString());
    }
}
