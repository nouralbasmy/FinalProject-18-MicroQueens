package com.example.payment.command;

import com.example.payment.model.Payment;

public class RefundCommand implements PaymentCommand {
    private final Payment payment;
//
    public RefundCommand(Payment payment) {
        this.payment = payment;
    }

    @Override
    public void execute() {
        payment.processRefund();
        //OR WILL SIMPLY DO THE CALL HERE
    }
}
