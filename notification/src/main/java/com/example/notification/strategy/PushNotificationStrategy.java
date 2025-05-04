package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;

@Component
public class PushNotificationStrategy implements NotificationStrategy{

    @Override
    public void send(Notification notification) {

        System.out.println("Push was sent to token "+notification.getDeviceToken()+ " " +notification.getMessage());
    }

}
