package com.example.payment.command;

import com.example.payment.model.Payment;

public class PayCommand implements PaymentCommand{

    private final Payment payment;

    public PayCommand(Payment payment)
    {
        this.payment = payment;
    }

    @Override
    public void execute() {
        payment.processPayment();
    }

}
