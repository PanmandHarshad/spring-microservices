package com.javatechie.saga.order.config;

import com.javatechie.dto.OrderRequestDto;
import com.javatechie.dto.OrderResponseDto;
import com.javatechie.event.OrderStatus;
import com.javatechie.event.PaymentStatus;
import com.javatechie.saga.order.entity.PurchaseOrder;
import com.javatechie.saga.order.repository.OrderRepository;
import com.javatechie.saga.order.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
        repository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    @Transactional
    private void updateOrder(PurchaseOrder purchaseOrder) {
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);

        if (!isPaymentComplete) {
            orderStatusPublisher.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
        }
    }

    public OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
        OrderRequestDto orderResponseDto = new OrderRequestDto();
        orderResponseDto.setOrderId(purchaseOrder.getId());
        orderResponseDto.setUserId(purchaseOrder.getUserId());
        orderResponseDto.setAmount(purchaseOrder.getPrice());
        orderResponseDto.setProductId(purchaseOrder.getProductId());
        return orderResponseDto;
    }
}
