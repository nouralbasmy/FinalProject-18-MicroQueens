package com.example.payment.command;

import com.example.payment.clients.CustomerClient;
import com.example.payment.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;

public class RefundCommand implements PaymentCommand {
    private final Long userId;
    private final double amount;
    @Autowired
    CustomerClient customerClient;

    public RefundCommand(Long userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    @Override
    public void execute() {
        customerClient.refund(userId, amount);
    }
}
