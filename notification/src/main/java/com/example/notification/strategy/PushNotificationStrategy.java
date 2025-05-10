package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;

@Component
public class PushNotificationStrategy implements NotificationStrategy{

    @Override
    public void send(Notification notification) {
        if (notification.getDeviceToken() == null || notification.getDeviceToken().isEmpty()) {
            throw new IllegalArgumentException("Device token is missing.");
        }

        // Simulating the push notification
        System.out.println("Simulating push notification to device token: " + notification.getDeviceToken());
        System.out.println("Message: " + notification.getMessage());
    }
    

}

