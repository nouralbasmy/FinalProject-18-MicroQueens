package com.example.payment.strategy;

import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.Payment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public void process(Payment payment) {
//        if (!(payment instanceof CreditCardPayment)) {
//            throw new IllegalArgumentException("Invalid payment type for Credit Card strategy");
//        }
        CreditCardPayment creditCardPayment = (CreditCardPayment) payment;
        validateCardholderName(creditCardPayment);
        validateCardNumber(creditCardPayment);
        validateExpirationDate(creditCardPayment);
//        System.out.println("Processing Credit Card payment for Order ID: " + creditCardPayment.getOrderId());
    }

    private void validateCardholderName(CreditCardPayment creditCardPayment) {
        if (creditCardPayment.getCardHolderName() == null || creditCardPayment.getCardHolderName().trim().isEmpty()) {
            throw new IllegalArgumentException("Cardholder name cannot be null or empty");
        }
    }

    private void validateCardNumber(CreditCardPayment creditCardPayment) {
        if (creditCardPayment.getCardNumber().length() != 16) {
            throw new IllegalArgumentException("Card number must be 16 digits");
        }
    }

    private void validateExpirationDate(CreditCardPayment creditCardPayment) {
        if (isExpired(creditCardPayment.getExpirationDate())) {
            throw new IllegalArgumentException("Credit card has expired");
        }
    }

    private boolean isExpired(String expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        LocalDate expiryDate = LocalDate.parse(expirationDate, formatter);
        LocalDate currentDate = LocalDate.now();
        return expiryDate.isBefore(currentDate);
    }
}