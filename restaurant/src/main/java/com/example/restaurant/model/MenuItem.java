package com.example.restaurant.model;

import com.example.restaurant.enums.DietaryOption;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MenuItems")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")

public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float price;
    private int inventory;
    @ElementCollection(targetClass = DietaryOption.class)
    @Enumerated(EnumType.STRING)
    private List<DietaryOption> dietaryRestrictions;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    // 1. No-arg constructor
    public MenuItem() {
    }

    // 2. All-args constructor (with ID)
    public MenuItem(Long id, String name, float price, int inventory,
            List<DietaryOption> dietaryRestrictions, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.dietaryRestrictions = dietaryRestrictions;
        this.restaurant = restaurant;
    }

    // 3. Constructor without ID (for creating new items)
    public MenuItem(String name, float price, int inventory,
            List<DietaryOption> dietaryRestrictions, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.dietaryRestrictions = dietaryRestrictions;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<DietaryOption> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<DietaryOption> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}