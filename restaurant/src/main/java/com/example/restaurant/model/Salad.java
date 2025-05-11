package com.example.restaurant.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SALAD")
public class Salad extends MenuItem {
    private String dressingType;
    private boolean hasProtein;
    private boolean isVegan;

    public Salad() {}

    public String getDressingType() {
        return dressingType;
    }

    public void setDressingType(String dressingType) {
        this.dressingType = dressingType;
    }

    public boolean isHasProtein() {
        return hasProtein;
    }

    public void setHasProtein(boolean hasProtein) {
        this.hasProtein = hasProtein;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setIsVegan(boolean vegan) {
        isVegan = vegan;
    }
}
