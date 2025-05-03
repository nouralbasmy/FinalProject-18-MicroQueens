package com.example.payment.strategy;

import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.Payment;

import java.time.LocalDate;
import java.time.YearMonth;
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
        //System.out.println("Processing Credit Card payment for Order ID: " + creditCardPayment.getOrderId());
    }

    private void validateCardholderName(CreditCardPayment creditCardPayment) {
        if (creditCardPayment.getCardHolderName() == null || creditCardPayment.getCardHolderName().trim().isEmpty()) {
            //System.out.println("henaaName");
            throw new IllegalArgumentException("Cardholder name cannot be null or empty");
        }
    }

    private void validateCardNumber(CreditCardPayment creditCardPayment) {
        if (creditCardPayment.getCardNumber().length() != 16) {
            //System.out.println("number");
            throw new IllegalArgumentException("Card number must be 16 digits");
        }
    }

    private void validateExpirationDate(CreditCardPayment creditCardPayment) {
        if (isExpired(creditCardPayment.getExpirationDate())) {
           // System.out.println("expired");
            throw new IllegalArgumentException("Credit card has expired");
        }
    }

    private boolean isExpired(String expirationDate) {
        //System.out.println("hi1");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expiryDate = YearMonth.parse(expirationDate, formatter);
        YearMonth currentDate = YearMonth.now();
        //System.out.println("hi"+ expiryDate.isBefore(currentDate));
        return expiryDate.isBefore(currentDate);
    }
}