package com.example.notification.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    //Order
    public static final String ORDER_NOTIFICATION_QUEUE = "order_notification_queue";
    public static final String ORDER_NOTIFICATION_EXCHANGE = "order_notification_exchange";
    public static final String ORDER_NOTIFICATION_ROUTING_KEY = "order_notification_key";

    //Customer
    public static final String CUSTOMER_NOTIFICATION_QUEUE = "customer_notification_queue";
    public static final String CUSTOMER_NOTIFICATION_EXCHANGE = "customer_notification_exchange";
    public static final String CUSTOMER_NOTIFICATION_KEY= "customer_notification_key";

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
    public Binding orderNotificationBinding() {
        return BindingBuilder
                .bind(orderNotificationQueue())
                .to(orderNotificationExchange())
                .with(ORDER_NOTIFICATION_ROUTING_KEY);
    }

    //Customer
    @Bean
    public Queue customerNotificationQueue() {
        return new Queue(CUSTOMER_NOTIFICATION_QUEUE);
    }

    @Bean
    public DirectExchange notificationCustomerExchange() {
        return new DirectExchange(CUSTOMER_NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(customerNotificationQueue())
                .to(notificationCustomerExchange())
                .with(CUSTOMER_NOTIFICATION_KEY);
    }

}