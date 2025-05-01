package com.example.payment.model;

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
}
