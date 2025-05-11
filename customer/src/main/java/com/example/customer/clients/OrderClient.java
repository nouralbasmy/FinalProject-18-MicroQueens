package com.example.customer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// @FeignClient(name = "order-service", url = "http://localhost:8090/order")
@FeignClient(name = "order-service", url = "${order.url}")
public interface OrderClient {
    @GetMapping("/restaurantId/{orderId}")
    Long getRestaurantIdByOrderId(@PathVariable Long orderId);

    @PutMapping("/customerOrderStatus/{orderId}")
    boolean customerUpdateOrder(@PathVariable Long orderId);
}
