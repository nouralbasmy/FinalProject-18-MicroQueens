package com.example.order.controller;
import com.example.order.clients.CustomerClient;
import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CustomerClient customerClient;

    @Autowired
    public OrderController(OrderService orderService, CustomerClient customerClient) {
        this.orderService = orderService;
        this.customerClient = customerClient;
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


    @GetMapping("/ordersByUserId")
    public List<Order> getOrdersByUserId(@RequestHeader("Authorization") String authHeader) {
        try {
//            return orderService.getOrdersByUserId(userId);
            Map<String, String> userInfo = customerClient.decodeToken(authHeader);
            if (userInfo == null || !userInfo.containsKey("userId")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            Long userId = Long.parseLong(userInfo.get("userId"));
            return orderService.filterOrders("userid",userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/ordersByStatus/{status}")
    public List<Order> getOrdersByStatus(@RequestHeader("Authorization") String authHeader, @PathVariable OrderStatus status) {
        try {
//            return orderService.getOrdersByStatus(status);
            Map<String, String> userInfo = customerClient.decodeToken(authHeader);
            if (userInfo == null || !userInfo.containsKey("userId")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            Long userId = Long.parseLong(userInfo.get("userId"));

            List<Order> allOrdersWithStatus = orderService.filterOrders("status",status);

            List<Order> filteredOrders = new ArrayList<>();
            for (Order order : allOrdersWithStatus) {
                if (userId.equals(order.getUserId())) {
                    filteredOrders.add(order);
                }
            }
            return filteredOrders;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found");
        }
    }


    @GetMapping("/ordersByDate/{date}")
    public List<Order> getOrdersByDate(@RequestHeader("Authorization") String authHeader, @PathVariable LocalDateTime date) {
        try {
//            return orderService.getOrdersByDate(date);
            Map<String, String> userInfo = customerClient.decodeToken(authHeader);
            if (userInfo == null || !userInfo.containsKey("userId")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            Long userId = Long.parseLong(userInfo.get("userId"));
            List<Order> orders = orderService.filterOrders("date",date);
            List<Order> filteredOrders = new ArrayList<>();
            for (Order order : orders) {
                if (userId.equals(order.getUserId())) {
                    filteredOrders.add(order);
                }
            }
            return filteredOrders;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Date not found");
        }
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestHeader("Authorization") String authHeader) {
        try {
            Map<String, String> userInfo = customerClient.decodeToken(authHeader);
            if (userInfo == null || !userInfo.containsKey("userId")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            Long userId = Long.parseLong(userInfo.get("userId"));
            orderService.placeOrder(userId);
            return ResponseEntity.ok("Order placed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to place order");
        }
    }

    //checkout cart (turning cart into order)
    @PostMapping("/checkout")
    public String checkout(@RequestHeader("Authorization") String authHeader, @RequestParam String paymentType, @RequestParam String extraInfo)
    {
        try{
            Map<String, String> userInfo = customerClient.decodeToken(authHeader);
            if (userInfo == null || !userInfo.containsKey("userId")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            Long userId = Long.parseLong(userInfo.get("userId"));
            Long orderId = orderService.checkout(authHeader,userId,paymentType,extraInfo);
            return "Checkout successful! Your OrderId: "+ orderId;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return "Failed to checkout! "+ e.getMessage();
        }
    }

    //----------FOR COMMUNICATION CALL WITH PAYMENT MICROSERVICE--------
    @PutMapping("/refundStatus/{orderId}")
    public void refundStatus(@PathVariable Long orderId) {
        try {
            orderService.refundStatus(orderId);

        } catch (Exception e) {
            //System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //----------FOR COMMUNICATION CALL WITH CUSTOMER MICROSERVICE--------
    @GetMapping("/restaurantId/{orderId}")
    public Long getRestaurantIdByOrderId(@PathVariable Long orderId) {
        Order order = getOrderById(orderId);
        return order.getRestaurantId();
    }

    @PutMapping("/customerOrderStatus/{orderId}")
    public boolean customerUpdateOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.updateStatus(orderId);
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
