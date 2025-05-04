package com.example.notification.strategy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;


@Component
public class NotificationSender {

    private final Map<String, NotificationStrategy> strategies;

    @Autowired
      public NotificationSender(
        EmailNotificationStrategy emailStrategy,
        SmsNotificationStrategy smsStrategy,
        PushNotificationStrategy pushStrategy) {
        
        this.strategies = Map.of(
            "email", emailStrategy,
            "EMAIL", emailStrategy,
            "sms", smsStrategy,
            "SMS", smsStrategy,
            "push", pushStrategy,
            "PUSH", pushStrategy
        );
    }


    public void send(String type, Notification notification){
        NotificationStrategy strategy = strategies.get(type);
        if(strategy == null){
            throw new IllegalArgumentException("This notification is null");
        }
        strategy.send(notification);
    }



}
