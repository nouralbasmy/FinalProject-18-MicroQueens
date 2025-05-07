package com.example.customer.model;


import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @ManyToOne
    private Customer customer;

    private Long restaurantId;




    // Getters
    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }


    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }


// Constructors

    // 1. No-arg constructor
    public Rating() {
    }

    // 2. All-args constructor
    public Rating(Long id, int score, Customer customer, Long restaurantId) {
        this.id = id;
        this.score = score;
        this.customer = customer;
        this.restaurantId = restaurantId;
    }

    // 3. Constructor without ID
    public Rating(int score, Customer customer, Long restaurantId ) {
        this.score = score;
        this.customer = customer;
        this.restaurantId = restaurantId;
    }


}