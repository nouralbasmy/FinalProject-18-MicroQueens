package com.example.notification.strategy;

import org.springframework.stereotype.Component;

import com.example.notification.model.EmailNotification;
import com.example.notification.model.Notification;
import com.example.notification.model.PushNotification;
import com.example.notification.model.SmsNotification;

@Component
public class NotificationSender {
    private final EmailNotificationStrategy emailStrategy;
    private final SmsNotificationStrategy  smsStrategy;
    private final PushNotificationStrategy pushStrategy;

    public NotificationSender(EmailNotificationStrategy emailStrategy, 
                              SmsNotificationStrategy  smsStrategy,
                              PushNotificationStrategy pushStrategy)
    {
        this.emailStrategy = emailStrategy;
        this.smsStrategy  = smsStrategy;
        this.pushStrategy = pushStrategy;
    }
    public void send(Notification notification){
        NotificationStrategy strategy;
        if (notification instanceof EmailNotification) {
            strategy = emailStrategy;
        } 
        else if (notification instanceof SmsNotification) {
            strategy = smsStrategy;
        } 
        
        else if (notification instanceof PushNotification) {
            strategy = pushStrategy;
        } 
        
        else {
            throw new IllegalArgumentException("Unsupported Notifications type: " + notification.getClass().getSimpleName());
        }

        strategy.send(notification);
    }

}
