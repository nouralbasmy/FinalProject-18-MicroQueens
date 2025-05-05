package com.example.order.strategy;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userid")
public class UserIdFilterStrategy implements OrderFilterStrategy{
    @Override
    public List<Order> filter(OrderRepository orderRepository, Object criteria) {
        Long userId = (Long) criteria;
        return orderRepository.getOrdersByUserId(userId);
    }
}
