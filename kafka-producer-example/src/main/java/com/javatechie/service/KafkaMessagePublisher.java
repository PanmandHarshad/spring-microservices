package com.javatechie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessageToTopic(String message) {
        // When you create an topic using default send method,
        // then default properties will be applied to the partition and replication
        // CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-demo1", message);

        // Use a topic which is already created
        // Behind the scene this topic is created with 3 partitions
        CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-demo-2", message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message = [" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }
}
