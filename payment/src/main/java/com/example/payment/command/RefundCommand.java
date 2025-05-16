package com.example.payment.command;

import com.example.payment.clients.CustomerClient;
import com.example.payment.clients.OrderClient;
import com.example.payment.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;

public class RefundCommand implements PaymentCommand {
    private final Long userId;
    private final double amount;
    private final Long orderId;



    private final CustomerClient customerClient;
    private final OrderClient orderClient;

    public RefundCommand(Long userId, double amount, Long orderId, CustomerClient customerClient, OrderClient orderClient) {
        this.userId = userId;
        this.amount = amount;
        this.orderId = orderId;
        this.customerClient = customerClient;
        this.orderClient = orderClient;
    }

    @Override
    public void execute() {
        customerClient.refund(userId, amount);
        orderClient.refundStatus(orderId);
    }
}
