package com.example.payment.command;

import com.example.payment.clients.CustomerClient;
import com.example.payment.clients.OrderClient;
import com.example.payment.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;

public class RefundCommand implements PaymentCommand {
    private final Long userId;
    private final double amount;
    private final Long orderId;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    OrderClient orderClient;

    public RefundCommand(Long userId, double amount, Long orderId) {
        this.userId = userId;
        this.amount = amount;
        this.orderId = orderId;
    }

    @Override
    public void execute() {
        customerClient.refund(userId, amount);
        orderClient.refundStatus(orderId);
    }
}
