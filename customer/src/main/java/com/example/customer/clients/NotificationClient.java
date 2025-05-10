package com.example.customer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", url = "http://localhost:8092/notification")
public interface NotificationClient {
    @PostMapping("/addNotification/{userId}")
    String addNotification(@PathVariable Long userId, @RequestParam Long restaurantId, @RequestParam Long orderId, @RequestParam String notificationType);
}
