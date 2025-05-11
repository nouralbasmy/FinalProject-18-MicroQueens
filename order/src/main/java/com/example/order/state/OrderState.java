package com.example.order.state;

import com.example.order.model.Order;

public interface OrderState {
    void next(Order order);

    void refund(Order order);
}
