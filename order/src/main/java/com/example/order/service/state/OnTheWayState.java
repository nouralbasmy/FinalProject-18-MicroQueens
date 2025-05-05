package com.example.order.service.state;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;

public class OnTheWayState implements OrderState {
    @Override
    public void next(Order order){
        order.setStatus(OrderStatus.DELIVERED);
    }

    @Override
    public String getStatus(){
        return OrderStatus.ON_THE_WAY.name();
    }
}
