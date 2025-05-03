package com.example.notification.model;

import java.time.LocalDateTime;

public class EmailNotification extends Notification{
    private String email; 

    public EmailNotification(){

    }
    public EmailNotification(Long userId, Long orderId, Long restaurantId, String message, boolean read, LocalDateTime notificationTime, String email){
        super(userId, orderId, restaurantId, message, read, notificationTime);
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
