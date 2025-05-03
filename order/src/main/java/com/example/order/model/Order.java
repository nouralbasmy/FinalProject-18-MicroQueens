package com.example.order.model;

import com.example.order.dto.OrderItem;
import jakarta.persistence.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name= "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    private LocalDateTime orderDate;
    private String status;
    private float totalPrice;
    private float discount;

    private String orderItemIds;

    public Order() {}

    public Order(long userId, LocalDateTime orderDate, String status, float totalPrice, float discount, List<String> orderItemIds) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.orderItemIds = orderItemIds;
    }

    public Order(long id, long userId, LocalDateTime orderDate, String status, float totalPrice, float discount, List<String> orderItemIds) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.orderItemIds = orderItemIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public List<String> getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(List<String> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }
}
