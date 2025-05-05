package com.example.order.strategy;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;

import java.util.List;

public interface OrderFilterStrategy {
    List<Order> filter(OrderRepository orderRepository, Object criteria);
}
