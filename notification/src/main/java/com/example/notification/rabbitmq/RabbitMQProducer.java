package com.example.notification.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToCustomer(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CUSTOMER_NOTIFICATION_EXCHANGE,
                RabbitMQConfig.CUSTOMER_NOTIFICATION_KEY,
                message
        );
        System.out.println("Notification Sent to customer: " + message);
    }
}