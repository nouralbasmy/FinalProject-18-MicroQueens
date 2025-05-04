package com.example.notification.model;

import java.time.LocalDateTime;

public class EmailNotification extends Notification{
    private String email; 

    public EmailNotification(){

    }
    public EmailNotification(Long userId, Long orderId, Long restaurantId, String message, boolean read, LocalDateTime notificationTime, String email){
        super(userId, orderId, restaurantId, message, read, notificationTime);
        setEmail(email);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("This variable should not be null");
        }
        this.email = email;
    }

    
}
