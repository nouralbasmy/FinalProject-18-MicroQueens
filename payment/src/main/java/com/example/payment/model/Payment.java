package com.example.payment.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private Long orderId;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Payment() {
    }

    public Payment(double amount, Long orderId, Long userId) {
        this.amount = amount;
        this.orderId = orderId;
        this.userId = userId;
    }

    public Payment(Long id, double amount, Long orderId, Long userId) {
        this.id = id;
        this.amount = amount;
        this.orderId = orderId;
        this.userId = userId;
    }

    public abstract void processPayment();

    public abstract void processRefund();
}
