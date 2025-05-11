package com.example.payment.factory;

import com.example.payment.model.Payment;

public interface PaymentFactory {
   Payment createPayment(double amount, Long orderId, Long userId, String additionalInfo);
}
