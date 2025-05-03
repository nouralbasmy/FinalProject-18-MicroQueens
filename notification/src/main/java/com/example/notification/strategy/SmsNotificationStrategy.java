package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;
import com.example.notification.model.SmsNotification;

@Component
public class SmsNotificationStrategy implements NotificationStrategy {

    @Override
    public void send(Notification notification) {
        SmsNotification sms = (SmsNotification) notification;
        System.out.println("SMS was sent to " + sms.getPhone()+ sms.getMessage());
    }

}
