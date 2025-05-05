package com.example.order.service.state;

import com.example.order.model.Order;

public interface OrderState {
    void next(Order order);
    String getStatus();
}
