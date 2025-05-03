package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.EmailNotification;
import com.example.notification.model.Notification;

@Component
public class EmailNotificationStrategy implements NotificationStrategy{

    @Override
    public void send(Notification notification) {
        EmailNotification email = (EmailNotification) notification;
        System.out.println("Email was sent to "+ email.getEmail() + " " + email.getMessage());

    }
  
}
