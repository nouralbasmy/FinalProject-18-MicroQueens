package com.example.order.model;

import com.example.order.state.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double totalPrice;
    private Long restaurantId;

    @Transient
    private OrderState state;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(long id, long userId, LocalDateTime orderDate, OrderStatus status, double totalPrice,
                 long restaurantId, List<OrderItem> orderItems) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.restaurantId = restaurantId;
        this.orderItems = orderItems;
    }

    public Order(long userId, LocalDateTime orderDate, OrderStatus status, double totalPrice, long restaurantId,
                 List<OrderItem> orderItems) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.restaurantId = restaurantId;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }


    public OrderState getState() {
        if(state == null)
        {
            switch (status) {
                case CONFIRMED -> state = new ConfirmedState();
                case ON_THE_WAY -> state = new OnTheWayState();
                case DELIVERED -> state = new DeliveredState();
                case REFUNDED -> state = new RefundedState();
            };
        }
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void next() {
        getState().next(this);
    }
}