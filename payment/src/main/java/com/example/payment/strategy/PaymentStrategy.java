package com.example.payment.strategy;

import com.example.payment.model.Payment;

public interface PaymentStrategy {
    void process(Payment payment);
}
