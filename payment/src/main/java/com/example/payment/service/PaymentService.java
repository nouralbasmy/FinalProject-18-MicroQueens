package com.example.payment.service;

import com.example.payment.factory.PaymentFactory;
import com.example.payment.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentFactory paymentFactory;

    @Autowired
    public PaymentService(PaymentFactory paymentFactory) {
        this.paymentFactory = paymentFactory;
    }

    public void processPayment(String paymentType, double amount, Long orderId, Long userId, String additionalInfo) {
        Payment payment = paymentFactory.createPayment(paymentType, amount, orderId, userId, additionalInfo);
        payment.pay();
    }
}
