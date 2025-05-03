package com.example.notification.model;

import java.time.LocalDateTime;

public class SmsNotification extends Notification{
    private String phone;


    public SmsNotification(){

    }

    public SmsNotification(Long userId, Long orderId, Long restaurantId, String message, boolean read, LocalDateTime notificationTime, String phone){
        super(userId, orderId, restaurantId, message, read, notificationTime);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    } 
        
        
}
