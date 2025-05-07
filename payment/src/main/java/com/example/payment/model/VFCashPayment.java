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


    @Override
    public void processPayment() {
        //Validation
        String walletNumber = this.getMobileWalletNumber();
        if (walletNumber == null || !walletNumber.matches("^010\\d{8}$")) {
            throw new IllegalArgumentException("Invalid mobile wallet number. It must be 11 digits and start with 010.");
        }
    }
}
