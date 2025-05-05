package com.example.order.strategy;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderFilterContext {
    private final Map<String, OrderFilterStrategy> strategies;

    public OrderFilterContext(Map<String, OrderFilterStrategy> strategies) {
        this.strategies = strategies;
    }

    public List<Order> filter(String filterType,OrderRepository orderRepository, Object criteria) {
        OrderFilterStrategy strategy = strategies.get(filterType.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for filter type: " + filterType);
        }
        return strategy.filter(orderRepository, criteria);
    }
}
