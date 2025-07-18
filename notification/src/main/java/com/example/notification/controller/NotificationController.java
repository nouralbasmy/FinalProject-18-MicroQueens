package com.example.notification.controller;

import com.example.notification.clients.CustomerClient;
import com.example.notification.model.Notification;
import com.example.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final CustomerClient customerClient;

    @Autowired
    public NotificationController(NotificationService notificationService, CustomerClient customerClient) {
        this.notificationService = notificationService;
        this.customerClient = customerClient;
    }

    @PostMapping("/addNotification")
    public String addNotification(@RequestHeader("Authorization") String authHeader, @RequestParam Long restaurantId, @RequestParam Long orderId, @RequestParam String notificationType) {
        Map<String, String> userInfo = customerClient.decodeToken(authHeader);
        if (userInfo == null || !userInfo.containsKey("userId")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        Long userId = Long.parseLong(userInfo.get("userId"));

        Notification notification = notificationService.addNotification(userId, restaurantId, orderId, notificationType);
        return notification.getMessage();
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable("id") String id) {
        return notificationService.getNotificationById(id);
    }

    @PutMapping("/updateNotification/{id}")
    public Notification updateNotification(@PathVariable String id ,@RequestBody Notification updatedNotification) {
        return notificationService.updateNotification(id, updatedNotification);
    }
    @DeleteMapping("/deleteNotification/{id}")
    public String deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
        return "Notification deleted";
    }

    @GetMapping("/greet")
    public String greetUser(@RequestParam(required = false, defaultValue = "Guest") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/ping")
    public String ping() {
        return "Service is up!";
    }

///////////////////////////////////////////////////

    @GetMapping("/user/getNotification")
    public List<Notification> getByUser(@RequestHeader("Authorization") String authHeader) {
        Map<String, String> userInfo = customerClient.decodeToken(authHeader);
        if (userInfo == null || !userInfo.containsKey("userId")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        Long userId = Long.parseLong(userInfo.get("userId"));
        return notificationService.getByUserId(userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Notification> getByRestaurant(@PathVariable Long restaurantId) {
        return notificationService.getByRestaurantId(restaurantId);
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable String id) {
        return notificationService.markAsRead(id);
    }

    @PutMapping("/{id}/unread")
    public Notification markAsUnread(@PathVariable String id) {
        return notificationService.markAsUnread(id);
    }

    @PutMapping("/{id}/status")
    public Notification updateStatus(@PathVariable String id, @RequestParam boolean read) {
        return notificationService.updateReadStatus(id, read);
    }

}
