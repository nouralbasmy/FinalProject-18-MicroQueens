package com.example.order.strategy;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("status")
public class StatusFilterStrategy implements OrderFilterStrategy{
    @Override
    public List<Order> filter(OrderRepository orderRepository, Object criteria) {
        OrderStatus status = (OrderStatus) criteria;
        return orderRepository.getOrdersByStatus(status);
    }
}
