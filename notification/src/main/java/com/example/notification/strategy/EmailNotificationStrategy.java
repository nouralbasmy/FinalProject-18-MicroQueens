package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;

@Component
public class EmailNotificationStrategy implements NotificationStrategy{

    @Override
    public void send(Notification notification) {
  
        System.out.println("Email was sent to "+ notification.getEmail() + " " + notification.getMessage());

    }
  
}
