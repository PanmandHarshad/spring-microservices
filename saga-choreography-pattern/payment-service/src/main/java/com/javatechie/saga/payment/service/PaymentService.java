package com.javatechie.saga.payment.service;

import com.javatechie.dto.OrderRequestDto;
import com.javatechie.dto.PaymentRequestDto;
import com.javatechie.event.OrderEvent;
import com.javatechie.event.PaymentEvent;
import com.javatechie.event.PaymentStatus;
import com.javatechie.saga.payment.entity.UserBalance;
import com.javatechie.saga.payment.entity.UserTransaction;
import com.javatechie.saga.payment.repository.UserBalanceRepository;
import com.javatechie.saga.payment.repository.UserTransactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceInDB() {
        userBalanceRepository.saveAll(Stream.of(new UserBalance(101, 5000),
                        new UserBalance(102, 3000),
                        new UserBalance(103, 4200),
                        new UserBalance(104, 20000),
                        new UserBalance(105, 999))
                .collect(Collectors.toList()));
    }

    /**
     * // Get the user id
     * // Check the balance availability
     * // If balance is sufficient -> Payment completed and deduct amount from DB
     * // If payment not sufficient -> cancel the order event and update the amount in DB
     *
     * @param orderEvent
     * @return
     */
    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
                orderRequestDto.getUserId(), orderRequestDto.getAmount());

        return userBalanceRepository.findById(orderRequestDto.getOrderId())
                .filter(userBalance -> userBalance.getPrice() > orderRequestDto.getAmount())
                .map(userBalance -> {
                    userBalance.setPrice(userBalance.getPrice() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),
                            orderRequestDto.getUserId(), orderRequestDto.getAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }


    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(userTransaction -> {
                    userTransactionRepository.delete(userTransaction);
                    userTransactionRepository.findById(userTransaction.getUserId())
                            .ifPresent(userBalance ->
                                    userBalance.setAmount(userBalance.getAmount() + userTransaction.getAmount()));

                });
    }
}
