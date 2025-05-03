package com.example.order.controller;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import com.example.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/captain")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/addOrder")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    @GetMapping("/allOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/updateStatus/{orderId}")
    public Order updateStatus(@PathVariable Long orderId) {
        try {
            return orderService.updateStatus(orderId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrderById(id);
            return "Order deleted successfully";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }


    @GetMapping("/ordersByUserId/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        try {
            return orderService.getOrdersByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/ordersByStatus/{status}")
    public List<Order> getOrdersByStatus(@PathVariable OrderStatus status) {
        try {
            return orderService.getOrdersByStatus(status);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found");
        }
    }


    @GetMapping("/ordersByDate/{date}")
    public List<Order> getOrdersByDate(@PathVariable LocalDateTime date) {
        try {
            return orderService.getOrdersByDate(date);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Date not found");
        }
    }




}
