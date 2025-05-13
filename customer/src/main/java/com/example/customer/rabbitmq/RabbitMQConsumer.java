package com.example.customer.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.clients.OrderClient;
import com.example.customer.model.Rating;
import com.example.customer.services.CustomerService;
import com.example.customer.services.RatingService;

@Service
public class RabbitMQConsumer {

    @Autowired
    OrderClient orderClient;



    @RabbitListener(queues = RabbitMQConfig.CUSTOMER_NOTIFICATION_QUEUE)
    public void consume(String message) {

        //String message = userId + ";" + restaurantId +";"+ order.getId();
        String[] parts = message.split(";", 3);
        Long orderId = Long.parseLong(parts[0]);
        // Long restaurantId = Long.valueOf(parts[1]);
        // String customMessage = parts[2];

        orderClient.customerUpdateOrder(orderId);
        System.out.println("Recieved From Notification to update");
    }
}
