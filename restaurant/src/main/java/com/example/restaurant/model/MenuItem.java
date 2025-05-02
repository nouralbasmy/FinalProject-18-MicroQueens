package com.example.restaurant.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MenuItems")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float price;
    private int inventory;
    @ElementCollection
    private List<String> dietaryRestrictions;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")  // This creates the foreign key in the menu_item table
    private Restaurant restaurant;


    // 1. No-arg constructor
    public MenuItem() {
    }

    // 2. All-args constructor (with ID)
    public MenuItem(int id, String name, float price, int inventory,
                    List<String> dietaryRestrictions, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.dietaryRestrictions = dietaryRestrictions;
        this.restaurant = restaurant;
    }

    // 3. Constructor without ID (for creating new items)
    public MenuItem(String name, float price, int inventory,
                    List<String> dietaryRestrictions, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.dietaryRestrictions = dietaryRestrictions;
        this.restaurant = restaurant;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public Restaurant getRestaurants() {
        return restaurant;
    }

    public void setRestaurants(Restaurant restaurant) {
        this.restaurant = restaurant;
    }



}