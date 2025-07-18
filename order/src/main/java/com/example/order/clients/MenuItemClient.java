package com.example.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @FeignClient(name = "restaurant-service", url = "http://localhost:8093/menuItems")
@FeignClient(name = "restaurant-service", url = "${restaurant.url}")
public interface MenuItemClient {
    @GetMapping("/checkInventory/{menuItemId}")
    boolean checkInventory(@PathVariable Long menuItemId, @RequestParam int quantity);

    @PutMapping("/decrementInventory/{menuItemId}")
    boolean decrementInventory(@PathVariable Long menuItemId, @RequestParam int quantity);
}
