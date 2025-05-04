package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;

@Component
public class SmsNotificationStrategy implements NotificationStrategy {

    @Override
    public void send(Notification notification) {
    
        System.out.println("Sending SMS: "+ notification.getPhone()  + notification.getMessage());
    }

}
