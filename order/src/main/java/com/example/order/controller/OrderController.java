package com.example.order.controller;
import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    
    private final OrderService orderService;

    @Autowired
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
//            return orderService.getOrdersByUserId(userId);
            return orderService.filterOrders("userid",userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/ordersByStatus/{status}")
    public List<Order> getOrdersByStatus(@PathVariable OrderStatus status) {
        try {
//            return orderService.getOrdersByStatus(status);
            return orderService.filterOrders("status",status);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found");
        }
    }


    @GetMapping("/ordersByDate/{date}")
    public List<Order> getOrdersByDate(@PathVariable LocalDateTime date) {
        try {
//            return orderService.getOrdersByDate(date);
            return orderService.filterOrders("date",date);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Date not found");
        }
    }


    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<String> placeOrder(@PathVariable Long userId) {
        try {
            orderService.placeOrder(userId);
            return ResponseEntity.ok("Order placed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to place order");
        }
    }
}
