package com.example.customer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Restaurant")
public interface RestaurantClient {
    @GetMapping("/restaurants/{restaurantId}/exists")
    boolean restaurantExists(@PathVariable("restaurantId") Long restaurantId);
}
