package com.example.order.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    public static final String ORDER_NOTIFICATION_QUEUE = "order_notification_queue";
    public static final String ORDER_NOTIFICATION_EXCHANGE = "order_notification_exchange";
    public static final String ORDER_NOTIFICATION_ROUTING_KEY = "order_notification_key";

    //Order
    @Bean
    public Queue orderNotificationQueue() {
        return new Queue(ORDER_NOTIFICATION_QUEUE);
    }

    @Bean
    public DirectExchange orderNotificationExchange() {
        return new DirectExchange(ORDER_NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding orderNotificationBinding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ORDER_NOTIFICATION_ROUTING_KEY);
    }

}