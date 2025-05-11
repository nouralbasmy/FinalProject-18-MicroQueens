package com.example.restaurant.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SANDWICH")
public class Sandwich extends MenuItem {
    private String breadType;
    private boolean isToasted;
    private String filling;

    public Sandwich() {}

    public String getBreadType() {
        return breadType;
    }

    public void setBreadType(String breadType) {
        this.breadType = breadType;
    }

    public boolean isToasted() {
        return isToasted;
    }

    public void setIsToasted(boolean toasted) {
        isToasted = toasted;
    }

    public String getFilling() {
        return filling;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }
}
