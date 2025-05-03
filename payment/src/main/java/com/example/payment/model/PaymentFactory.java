package com.example.payment.model;

public class PaymentFactory {
    public static Payment createPayment(
            String type,
            double amount,
            Long orderId,
            Long userId,
            String billingAddress,
            String mobileWalletNumber,
            String cardNumber,
            String cardHolderName,
            String expirationDate
    ) {
        switch (type.toUpperCase()) {
            case "COD":
                return new CODPayment(amount, orderId, userId, billingAddress);
            case "VFCASH":
                return new VFCashPayment(amount, orderId, userId, mobileWalletNumber);
            case "CREDIT_CARD":
                return new CreditCardPayment(amount, orderId, userId, cardNumber, cardHolderName, expirationDate);
            default:
                throw new IllegalArgumentException("Invalid payment type: " + type);
        }
    }
}
