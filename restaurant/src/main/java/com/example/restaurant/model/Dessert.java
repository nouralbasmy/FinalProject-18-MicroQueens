package com.example.restaurant.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DESSERT")
public class Dessert extends MenuItem {
    private boolean isGlutenFree;
    private boolean containsNuts;
    private String servingTemperature; // cold, warm

    public Dessert() {}

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public void setIsGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
    }

    public boolean isContainsNuts() {
        return containsNuts;
    }

    public void setContainsNuts(boolean containsNuts) {
        this.containsNuts = containsNuts;
    }

    public String getServingTemperature() {
        return servingTemperature;
    }

    public void setServingTemperature(String servingTemperature) {
        this.servingTemperature = servingTemperature;
    }
}
