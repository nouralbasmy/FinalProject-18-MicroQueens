package com.example.payment.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VFCash")
public class VFCashPayment extends Payment{
    private String mobileWalletNumber;

    public VFCashPayment() {
    }

    public VFCashPayment(double amount, Long orderId, Long userId, String mobileWalletNumber) {
        super(amount, orderId, userId);
        this.mobileWalletNumber = mobileWalletNumber;
    }

    public String getMobileWalletNumber() {
        return mobileWalletNumber;
    }

    public void setMobileWalletNumber(String mobileWalletNumber) {
        this.mobileWalletNumber = mobileWalletNumber;
    }
}
