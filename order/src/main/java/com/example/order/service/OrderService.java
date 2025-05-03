package com.example.order.service;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.service.state.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
           throw new RuntimeException("Order not found");
        }
        return order.get();
    }

    public OrderState getState(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case PENDING -> new PendingState();
            case CONFIRMED -> new ConfirmedState();
            case ON_THE_WAY -> new OnTheWayState();
            case DELIVERED -> new DeliveredState();
        };
    }

    public Order updateStatus(Long orderId) {
        Order order = getOrderById(orderId);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        OrderStatus currentStatus = order.getStatus();
        OrderState currentState = getState(currentStatus);

        currentState.next(order);

        return orderRepository.save(order);
    }

    public void deleteOrderById(Long orderId) {
        Order order = getOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.getOrdersByUserId(userId);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.getOrdersByStatus(status);
    }

    public List<Order> getOrdersByDate(LocalDateTime date){
        return orderRepository.getOrdersByOrderDate(date);
    }
}
