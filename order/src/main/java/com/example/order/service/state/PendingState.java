package com.example.order.service.state;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;

public class PendingState implements OrderState {
    @Override
    public void next(Order order){
        order.setStatus(OrderStatus.CONFIRMED);
    }

    @Override
    public String getStatus(){
        return OrderStatus.PENDING.name();
    }

}
