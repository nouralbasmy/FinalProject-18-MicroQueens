package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;
import com.example.notification.model.PushNotification;

@Component
public class PushNotificationStrategy implements NotificationStrategy{

    @Override
    public void send(Notification notification) {
        PushNotification pushed = (PushNotification) notification;
        System.out.println("Push was sent to token "+pushed.getDeviceToken()+pushed.getMessage());
    }

}
