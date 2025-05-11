package com.example.order.state;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;

public class DeliveredState implements OrderState{
    @Override
    public void next(Order order){
        throw new IllegalStateException("Order already delivered");
    }

    @Override
    public void refund(Order order){
        order.setStatus(OrderStatus.REFUNDED);
        order.setState(new RefundedState());
    }
}
