package com.example.notification.strategy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;


@Component
public class NotificationSender {

    // private final Map<String, NotificationStrategy> strategies;
    private static final String STRATEGY_PACKAGE = "com.example.notification.strategy";
    private final ApplicationContext appContext;  

    public NotificationSender(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public void send(String type, Notification notification){
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("This type is null");
        }
        //for example if type is email so it becomes Email (capitalizing first letter)
        String className = STRATEGY_PACKAGE + "." + capitalize(type.toLowerCase()) + "NotificationStrategy";
        try {
            Class<?> clazz = Class.forName(className);
            NotificationStrategy strategy = (NotificationStrategy) clazz.getDeclaredConstructor().newInstance();
            appContext.getAutowireCapableBeanFactory().autowireBean(strategy);
            strategy.send(notification);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("No strategy found for this type");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create strategy for this type");
        }
    }
    private String capitalize(String string) {
        if (string == null || string.isEmpty()) return string;
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
        // NotificationStrategy strategy = strategies.get(type);
        // if(strategy == null){
        //     throw new IllegalArgumentException("This notification is null");
        // }
        // strategy.send(notification);
    }



    // @Autowired
    //   public NotificationSender(
    //     EmailNotificationStrategy emailStrategy,
    //     SmsNotificationStrategy smsStrategy,
    //     PushNotificationStrategy pushStrategy) {
        
    //     this.strategies = Map.of(
    //         "email", emailStrategy,
    //         "EMAIL", emailStrategy,
    //         "sms", smsStrategy,
    //         "SMS", smsStrategy,
    //         "push", pushStrategy,
    //         "PUSH", pushStrategy
    //     );
    // }




