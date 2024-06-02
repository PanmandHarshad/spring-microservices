package com.javatechie.controller;

import com.javatechie.dto.Customer;
import com.javatechie.service.KafkaEventMessagePublisher;
import com.javatechie.service.KafkaPlainTextMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private KafkaPlainTextMessagePublisher plainTextMessagePublisher;

    @Autowired
    private KafkaEventMessagePublisher eventMessagePublisher;

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message) {
        try {
            for (int i = 0; i < 10_000; i++) {
                plainTextMessagePublisher.sendMessageToTopic(i + " : " + message);
            }
            return ResponseEntity.ok("Message published successfully ..");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/publish")
    public void sendEvents(@RequestBody Customer customer) {
        eventMessagePublisher.sendEventsToTopic(customer);
    }
}
