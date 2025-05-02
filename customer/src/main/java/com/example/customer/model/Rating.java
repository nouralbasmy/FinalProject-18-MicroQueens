package com.example.customer.model;

import com.example.restaurant.model.Restaurant;
import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Restaurant restaurant;



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

    public Restaurant getRestaurant() {
        return restaurant;
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

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


// Constructors

    // 1. No-arg constructor
    public Rating() {
    }

    // 2. All-args constructor
    public Rating(Long id, int score, Customer customer, Restaurant restaurant) {
        this.id = id;
        this.score = score;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    // 3. Constructor without ID
    public Rating(int score, Customer customer, Restaurant restaurant ) {
        this.score = score;
        this.customer = customer;
        this.restaurant = restaurant;
    }

}