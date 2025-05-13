package com.example.customer.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String CUSTOMER_NOTIFICATION_QUEUE = "customer_notification_queue";
    public static final String CUSTOMER_NOTIFICATION_EXCHANGE = "customer_notification_exchange";
    public static final String CUSTOMER_NOTIFICATION_ROUTING_KEY = "customer_notification_key";

    //Order
    @Bean
    public Queue orderNotificationQueue() {
        return new Queue(CUSTOMER_NOTIFICATION_QUEUE);
    }

    @Bean
    public DirectExchange orderNotificationExchange() {
        return new DirectExchange(CUSTOMER_NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding orderNotificationBinding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(CUSTOMER_NOTIFICATION_ROUTING_KEY);
    }

}