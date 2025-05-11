package com.example.payment.factory;

import com.example.payment.model.Payment;
import com.example.payment.model.VFCashPayment;

public class VFCashPaymentFactory implements PaymentFactory{
    @Override
    public Payment createPayment(double amount, Long orderId, Long userId, String additionalInfo) {
        return new VFCashPayment(amount, orderId, userId, additionalInfo);  // mobileWalletNumber
    }
}
