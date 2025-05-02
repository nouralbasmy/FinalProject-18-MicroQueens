package com.example.restaurant.model;

import jakarta.persistence.*;

import java.awt.*;
import java.util.List;
@Entity
@Table(name = "Restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String cuisine;
    private String address;
    private String phone;
    private int rating;
    private boolean active;

    @ElementCollection
    private List<String> dietaryOptions;


    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menu;


    // 1. No-arg constructor (required by JPA)
    public Restaurant() {
    }

    // 2. All-args constructor (with ID)
    public Restaurant(int id, String name, String cuisine, String address, String phone,
                      int rating, boolean active, List<String> dietaryOptions, List<MenuItem> menu) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.active = active;
        this.dietaryOptions = dietaryOptions;
        this.menu = menu;
    }

    // 3. Constructor without ID (useful for creating new records)
    public Restaurant(String name, String cuisine, String address, String phone,
                      int rating, boolean active, List<String> dietaryOptions, List<MenuItem> menu) {
        this.name = name;
        this.cuisine = cuisine;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.active = active;
        this.dietaryOptions = dietaryOptions;
        this.menu = menu;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getDietaryOptions() {
        return dietaryOptions;
    }

    public void setDietaryOptions(List<String> dietaryOptions) {
        this.dietaryOptions = dietaryOptions;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }


}