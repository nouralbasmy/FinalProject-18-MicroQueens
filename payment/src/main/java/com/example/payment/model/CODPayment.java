package com.example.payment.model;

import com.example.payment.strategy.PaymentStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COD")
public class CODPayment extends Payment{
    private String billingAddress;

    public CODPayment() {
    }

    public CODPayment(double amount, Long orderId, Long userId, String billingAddress) {
        super(amount, orderId, userId);
        this.billingAddress = billingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public void processPayment() {
        //CASH ON DELIVERY LOGIC HEREEEE
        //Validation
        if (this.getBillingAddress() == null || this.getBillingAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Billing address cannot be null or empty");
        }
        //for each item, inventory - quantity!!!


    }

    @Override
    public void processRefund() {
        //CASH ON DELIVERY LOGIC HEREEEE
        //Wallet+amount
    }


}
