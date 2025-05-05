package com.example.notification.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;

@Component
public class EmailNotificationStrategy implements NotificationStrategy{

    @Autowired
    private JavaMailSender mailSender;

 

    @Override
    public void send(Notification notification) {
        Notification e = (Notification) notification;

        if(e.getEmail()==null ||  e.getEmail().isBlank()) {
            throw new IllegalArgumentException("email required");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(e.getEmail());
        message.setSubject("QuickBite - order #"+ e.getOrderId());
        message.setText("""
                Hello,

                %s

                Order ID: %d
                Restaurant ID: %d

                Thank you for ordering from our restaurant QuickBite!

                """.formatted(e.getMessage(), e.getOrderId(), e.getRestaurantId()));

        mailSender.send(message);
        System.out.println("Email sent to " + e.getEmail());
}
}
