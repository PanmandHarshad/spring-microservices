package com.javatechie.saga.order.config;

import com.javatechie.event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {

    @Autowired
    private OrderStatusUpdateHandler orderStatusUpdateHandler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {
        // Listen payment-event topic
        // Will check payment status
        // If payment status completed -> complete the order
        // If payment status failed -> cancel the order
        return paymentEvent -> orderStatusUpdateHandler.updateOrder(paymentEvent.getPaymentRequestDto().getOrderId(),
                purchaseOrder -> {
                    purchaseOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
                });
    }
}
