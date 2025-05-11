package com.example.payment.factory;

import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.Payment;

public class CreditCardPaymentFactory implements PaymentFactory{
    @Override
    public Payment createPayment(double amount, Long orderId, Long userId, String additionalInfo) {
        String[] cardDetails = additionalInfo.split(","); // assuming details are separated by commas
        return new CreditCardPayment(amount, orderId, userId, cardDetails[0], cardDetails[1], cardDetails[2]); // cardNumber, cardHolderName, expirationDate
    }
}
