package com.example.order.state;

import com.example.order.model.Order;

public class RefundedState implements OrderState{
    @Override
    public void next(Order order){
        throw new IllegalStateException("Order already delivered");
    }

    @Override
    public void refund(Order order){
        throw new IllegalStateException("Order already refunded");
    }
}
