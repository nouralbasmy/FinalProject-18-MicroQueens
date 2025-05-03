package com.example.notification.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class PushNotification extends Notification{
    private String deviceToken;

    public PushNotification(){

    }

    public PushNotification(Long userId, Long orderId, Long restaurantId, String message, boolean read, LocalDateTime notificationTime, String deviceToken){
        super(userId, orderId, restaurantId, message, read, notificationTime);
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


}
