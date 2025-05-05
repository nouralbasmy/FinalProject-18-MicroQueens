package com.example.payment.model;

import com.example.payment.strategy.PaymentStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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
        //for each item, inventory - quantity

    }

    @Override
    public void processRefund() {
        //CreditCard LOGIC HEREEEE
        //Validation
        //Wallet+amount
    }
}
