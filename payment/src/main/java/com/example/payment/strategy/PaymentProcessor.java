package com.example.payment.strategy;

import com.example.payment.model.CODPayment;
import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.Payment;
import com.example.payment.model.VFCashPayment;

public class PaymentProcessor {
    public void processPayment(Payment payment) {
        PaymentStrategy strategy;

        if (payment instanceof CODPayment) {
            strategy = new CODPaymentStrategy();
        } else if (payment instanceof VFCashPayment) {
            strategy = new VFCashPaymentStrategy();
        } else if (payment instanceof CreditCardPayment) {
            strategy = new CreditCardPaymentStrategy();
        } else {
            throw new IllegalArgumentException("Unsupported payment type: " + payment.getClass().getSimpleName());
        }

        strategy.process(payment);
    }

}
