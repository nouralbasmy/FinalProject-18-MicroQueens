package com.example.order.model;

import java.util.ArrayList;

public class Cart {

    private Long id;
    private Long userId;
    private List<OrderItem> orderItems;
    private double totalPrice;

    public Cart(Long id, Long userId, List<OrderItem> orderItems, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    public Cart(Long userId, List<OrderItem> orderItems, double totalPrice) {
        this.userId = userId;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    public Cart() {
        this.orderItems = new ArrayList<OrderItem>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
