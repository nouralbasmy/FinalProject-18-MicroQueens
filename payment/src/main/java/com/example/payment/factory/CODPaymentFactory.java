package com.example.payment.factory;

import com.example.payment.model.CODPayment;
import com.example.payment.model.Payment;

public class CODPaymentFactory implements PaymentFactory {
    @Override
    public Payment createPayment(double amount, Long orderId, Long userId, String additionalInfo) {
        return new CODPayment(amount, orderId, userId, additionalInfo);  // billingAddress
    }
}
