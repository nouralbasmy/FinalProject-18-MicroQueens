package com.example.notification.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,              
        include = JsonTypeInfo.As.PROPERTY,       
        property = "@class"                       
)
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private Long userId;
    private Long orderId;
    private Long restaurantId;
    private String message;
    private boolean read = false;
    private LocalDateTime notificationTime = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Notification() {
    }

    public Notification(String id, Long userId, Long orderId, Long restaurantId, String message, boolean read, LocalDateTime notificationTime) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.message = message;
        this.read = read;
        this.notificationTime = notificationTime;
    }

    public Notification(Long userId, Long orderId, Long restaurantId, String message, boolean read, LocalDateTime notificationTime) {
        this.userId = userId;
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.message = message;
        this.read = read;
        this.notificationTime = notificationTime;
    }

    public Notification(String message) {
        this.message = message;
    }
}
