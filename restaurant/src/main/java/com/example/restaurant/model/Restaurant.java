package com.example.restaurant.model;

import jakarta.persistence.*;

import java.awt.*;
import com.example.restaurant.enums.Cuisine;
import com.example.restaurant.enums.DietaryOption;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phone;
    private int openTime; // e.g., 9 hour only
    private int closeTime; // e.g., 22

    private boolean active;
    private double avgRating;
    private long totalRatings;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DietaryOption> dietaryOptions;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Cuisine> cuisines;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menu;

    // 1. No-arg constructor (required by JPA)
    public Restaurant() {
    }

    // 2. All-args constructor (with ID)
    public Restaurant(Long id, String name, List<Cuisine> cuisines, String address, String phone,
            int openTime, int closeTime,
            double avgRating, boolean active, List<DietaryOption> dietaryOptions, List<MenuItem> menu,
            long totalRatings) {
        this.id = id;
        this.name = name;
        this.cuisines = cuisines;
        this.address = address;
        this.phone = phone;
        this.avgRating = avgRating;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.active = active;
        this.dietaryOptions = dietaryOptions;
        this.menu = menu;
        this.totalRatings = totalRatings;
    }

    // 3. Constructor without ID (useful for creating new records)
    public Restaurant(String name, List<Cuisine> cuisines, String address, String phone, double avgRating,
            int openTime, int closeTime, boolean active,
            List<DietaryOption> dietaryOptions, List<MenuItem> menu, long totalRatings) {
        this.name = name;
        this.cuisines = cuisines;
        this.address = address;
        this.phone = phone;
        this.avgRating = avgRating;
        this.active = active;
        this.dietaryOptions = dietaryOptions;
        this.menu = menu;
        this.totalRatings = totalRatings;
        this.openTime = openTime;
        this.closeTime = closeTime;

    }

    // 4 logical constructor
    public Restaurant(String name, List<Cuisine> cuisines, String address, String phone,
            List<DietaryOption> dietaryOptions, List<MenuItem> menu) {
        this.name = name;
        this.cuisines = cuisines;
        this.address = address;
        this.phone = phone;
        this.avgRating = 0;
        this.dietaryOptions = dietaryOptions;
        this.menu = menu;
        this.totalRatings = 0;
        this.openTime = 9; // 9:00 AM
        this.closeTime = 22; // 10:00 PM

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

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
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

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getOpenTime() {
        return this.openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloseTime() {
        return this.closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<DietaryOption> getDietaryOptions() {
        return dietaryOptions;
    }

    public void setDietaryOptions(List<DietaryOption> dietaryOptions) {
        this.dietaryOptions = dietaryOptions;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

}