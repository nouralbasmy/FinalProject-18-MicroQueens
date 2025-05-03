package com.example.payment.model;

import com.example.payment.strategy.PaymentStrategy;
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
    public void pay() {
        //VF Cash LOGIC HEREEE
        System.out.println("VF CASHHH HEREEE!");
    }
}
