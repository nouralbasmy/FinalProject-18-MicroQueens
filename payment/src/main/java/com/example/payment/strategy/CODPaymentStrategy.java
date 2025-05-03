package com.example.payment.strategy;

import com.example.payment.model.CODPayment;
import com.example.payment.model.Payment;

public class CODPaymentStrategy implements PaymentStrategy{
    @Override
    public void process(Payment payment) {
//        if (!(payment instanceof CODPayment)) {
//            throw new IllegalArgumentException("Invalid payment type for Cash on Delivery strategy");
//        }
        CODPayment codPayment = (CODPayment) payment;
        validateBillingAddress(codPayment);
    }

    private void validateBillingAddress(CODPayment codPayment) {
        if (codPayment.getBillingAddress() == null || codPayment.getBillingAddress().isEmpty()) {
            throw new IllegalArgumentException("Billing address cannot be null or empty");
        }
    }

}
