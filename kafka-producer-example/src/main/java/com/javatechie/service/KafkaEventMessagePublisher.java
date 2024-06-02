package com.javatechie.service;

import com.javatechie.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.javatechie.constants.KafkaTopicNames.TOPIC_NAME_CUSTOMER_DATA;

@Service
public class KafkaEventMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendEventsToTopic(Customer customer) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send(TOPIC_NAME_CUSTOMER_DATA, customer);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent customer = [" + customer.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send customer=[" +
                            customer.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }
}
