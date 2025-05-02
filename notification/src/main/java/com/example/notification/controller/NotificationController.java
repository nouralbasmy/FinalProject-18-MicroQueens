package com.example.notification.controller;

import com.example.notification.model.Notification;
import com.example.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/addNotification")
    public Notification addNotification(@RequestBody Notification notification) {
        return notificationService.addNotification(notification);
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


}
