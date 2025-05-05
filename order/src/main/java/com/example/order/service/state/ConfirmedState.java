package com.example.order.service.state;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;

public class ConfirmedState implements OrderState {
    @Override
    public void next(Order order){
        order.setStatus(OrderStatus.ON_THE_WAY);
        order.setState(new OnTheWayState());
    }


    @Override
    public String getStatus(){
        return OrderStatus.CONFIRMED.name();
    }
}
