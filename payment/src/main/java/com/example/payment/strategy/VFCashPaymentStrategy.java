package com.example.payment.strategy;

import com.example.payment.model.Payment;
import com.example.payment.model.VFCashPayment;

public class VFCashPaymentStrategy implements PaymentStrategy {

    @Override
    public void process(Payment payment) {
//        if (!(payment instanceof VFCashPayment)) {
//            throw new IllegalArgumentException("Invalid payment type for Vodafone Cash strategy");
//        }

        VFCashPayment vfCashPayment = (VFCashPayment) payment;
        validateWalletNumber(vfCashPayment);
        //System.out.println("Processing Vodafone Cash for mobile wallet number: " + vfCashPayment.getMobileWalletNumber());
    }

    private void validateWalletNumber(VFCashPayment vfCashPayment) {
        String walletNumber = vfCashPayment.getMobileWalletNumber();
        if (walletNumber == null || !walletNumber.matches("^010\\d{8}$")) {
            throw new IllegalArgumentException("Invalid mobile wallet number. It must be 11 digits and start with 010.");
        }
    }
}
