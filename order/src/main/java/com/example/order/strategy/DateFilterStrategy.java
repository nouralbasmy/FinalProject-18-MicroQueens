package com.example.order.strategy;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("date")
public class DateFilterStrategy implements OrderFilterStrategy{
    @Override
    public List<Order> filter(OrderRepository orderRepository, Object criteria) {
        LocalDateTime date = (LocalDateTime) criteria;
        return orderRepository.getOrdersByOrderDate(date);
    }
}
