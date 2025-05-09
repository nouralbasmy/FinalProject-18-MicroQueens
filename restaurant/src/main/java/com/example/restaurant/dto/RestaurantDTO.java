package com.example.restaurant.dto;

import com.example.restaurant.enums.Cuisine;
import com.example.restaurant.enums.DietaryOption;

import java.util.List;

public class RestaurantDTO {
    private Long id;
    private String name;
    private List<Cuisine> cuisines;
    private List<DietaryOption> dietaryOptions;
    private String address;
    private boolean active;
    private double avgRating;

    public RestaurantDTO() {

    }
    public RestaurantDTO(Long id, String name, List<Cuisine> cuisines, List<DietaryOption> dietaryOptions,String address, boolean active, double avgRating) {
        this.id = id;
        this.name = name;
        this.cuisines = cuisines;
        this.dietaryOptions = dietaryOptions;
        this.address = address;
        this.active = active;
        this.avgRating = avgRating;

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
    public List<DietaryOption> getDietaryOptions() {
        return dietaryOptions;
    }
    public void setDietaryOptions(List<DietaryOption> dietaryOptions) {
        this.dietaryOptions = dietaryOptions;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public double getAvgRating() {
        return avgRating;
    }
    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

}
