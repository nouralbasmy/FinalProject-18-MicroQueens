package com.example.order.state;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;

public class OnTheWayState implements OrderState {
    @Override
    public void next(Order order){
        order.setStatus(OrderStatus.DELIVERED);
        order.setState(new DeliveredState());
    }

    @Override
    public void refund(Order order){
        throw new IllegalStateException("Can not refund order now, it is on the way");
    }
}
