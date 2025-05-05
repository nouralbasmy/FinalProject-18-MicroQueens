package com.example.notification.strategy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.notification.model.Notification;

@Component
public class SmsNotificationStrategy implements NotificationStrategy {

    @Override
    public void send(Notification notification) {
        if (notification.getPhone() == null || notification.getPhone().isBlank())
           throw new IllegalArgumentException("phone required");

        //simulation since everything is very costly :(
           System.out.printf(
            """
            ─── Simulated SMS ───────────────────────────────────────
            To      : %s
            Order   : %d
            Restaurant : %d
            Message : %s
            ─────────────────────────────────────────────────────────
            """,
            notification.getPhone(),
            notification.getOrderId(),
            notification.getRestaurantId(),
            notification.getMessage()
        );

       
    
    }

}
