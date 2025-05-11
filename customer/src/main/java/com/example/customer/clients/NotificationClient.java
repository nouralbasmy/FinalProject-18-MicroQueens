package com.example.customer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notification-service", url = "http://localhost:8092/notification")
public interface NotificationClient {
    @PostMapping("/addNotification")
    String addNotification(@RequestHeader("Authorization") String authHeader, @RequestParam Long restaurantId, @RequestParam Long orderId, @RequestParam String notificationType);
}
