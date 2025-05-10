package com.example.notification.rabbitmq;

import com.example.notification.model.Notification;
import com.example.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @Autowired
    NotificationService notificationService;


    @RabbitListener(queues = RabbitMQConfig.ORDER_NOTIFICATION_QUEUE)
    public void consume(String message) {

        //String message = userId + ";" + restaurantId +";"+ order.getId();
        String[] parts = message.split(";", 3);
        Long userId  = Long.valueOf(parts[0]);
        Long restaurantId  = Long.valueOf(parts[1]);
        Long orderId = Long.valueOf(parts[2]);

        Notification notification = new Notification(userId,orderId,restaurantId,"email",false, "");
        notificationService.addNotification(notification);

    }
}
