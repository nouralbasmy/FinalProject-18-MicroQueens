package com.example.notification.strategy;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;


@Component
public class NotificationSender {
    private final ApplicationContext appContext;


    public NotificationSender(ApplicationContext appContext)
    {
        this.appContext = appContext;
        
    }
    public void send(Notification notification){
        try{
            String notificationClassName = notification.getClass().getName(); // now lets say it is EmailNotification
            String strategyClassName = notificationClassName.replace(".model.", ".strategy.") + "Strategy";

            //reflection
            Class<?> clazz = Class.forName(strategyClassName);
            // retrieve the bean from spring
            NotificationStrategy strategy = (NotificationStrategy) appContext.getBean(clazz);

            strategy.send(notification);
        }catch(ClassNotFoundException e){
            throw new IllegalArgumentException("Unsupported Notifications type: " + notification.getClass().getSimpleName());
        }
       

    }

}
