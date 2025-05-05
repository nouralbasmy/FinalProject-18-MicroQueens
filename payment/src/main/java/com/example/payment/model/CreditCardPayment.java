package com.example.payment.model;

import com.example.payment.strategy.PaymentStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("CREDIT_CARD")
public class CreditCardPayment extends Payment{

    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;

    public CreditCardPayment() {
    }

    public CreditCardPayment(double amount, Long orderId, Long userId, String cardNumber, String cardHolderName, String expirationDate) {
        super(amount, orderId, userId);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate =expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }


    @Override
    public void processPayment() {
        //CreditCard LOGIC HEREEEE
        //Validation
        if (this.getCardHolderName() == null || this.getCardHolderName().trim().isEmpty()) {
            //System.out.println("henaaName");
            throw new IllegalArgumentException("Cardholder name cannot be null or empty");
        }
        if (this.getCardNumber().length() != 16) {
            //System.out.println("number");
            throw new IllegalArgumentException("Card number must be 16 digits");
        }
        if (isExpired(this.getExpirationDate())) {
            // System.out.println("expired");
            throw new IllegalArgumentException("Credit card has expired");
        }

        //for each item, inventory - quantity!!

    }

    @Override
    public void processRefund() {
        //CreditCard LOGIC HEREEE
        //Wallet+amount
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
