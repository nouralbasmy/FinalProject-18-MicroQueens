package com.example.payment.command;

import com.example.payment.model.Payment;
import com.example.payment.service.PaymentService;

public class RefundCommand implements PaymentCommand{
    private final Payment payment;
    private final PaymentService paymentService;

    public RefundCommand(Payment payment, PaymentService paymentService) {
        this.payment = payment;
        this.paymentService = paymentService;
    }

    @Override
    public void execute() {
        paymentService.processRefund(payment);

    }
}
