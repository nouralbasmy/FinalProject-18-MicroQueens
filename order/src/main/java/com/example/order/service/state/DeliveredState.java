package com.example.order.service.state;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;

public class DeliveredState implements OrderState{
    @Override
    public void next(Order order){
        throw new IllegalStateException("Order already delivered");
    }

    @Override
    public String getStatus(){
        return OrderStatus.DELIVERED.name();
    }
}
