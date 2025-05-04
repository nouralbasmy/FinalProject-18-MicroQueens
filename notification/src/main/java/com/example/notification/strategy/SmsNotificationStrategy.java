package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;
import com.example.notification.model.SmsNotification;

@Component
public class SmsNotificationStrategy implements NotificationStrategy {

    @Override
    public void send(Notification notification) {
        SmsNotification sms = (SmsNotification) notification;
        if (sms.getPhone() == null || sms.getPhone().isBlank()) {
            throw new IllegalArgumentException("this variable is required");
        }
        System.out.println("SMS was sent to " + sms.getPhone()+ " " + sms.getMessage());
    }

}
