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
    public static final String CUSTOMER_NOTIFICATION_KEY = "customer_notification_key";

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