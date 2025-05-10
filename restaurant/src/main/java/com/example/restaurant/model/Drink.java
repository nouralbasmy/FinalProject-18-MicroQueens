package com.example.restaurant.model;

import com.example.restaurant.enums.DietaryOption;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DRINK")
public class Drink extends MenuItem {

    private boolean isCold;
    private String volume; // 330ml, 500ml
    private String flavor; // mango, cola, etc.

    public Drink() {
        // No-arg constructor
    }

    public Drink(String name, float price, int inventory, java.util.List<DietaryOption> dietaryRestrictions,
                 Restaurant restaurant, boolean isCold, String volume, String flavor) {
        super(name, price, inventory, dietaryRestrictions, restaurant);
        this.isCold = isCold;
        this.volume = volume;
        this.flavor = flavor;
    }

    public boolean isCold() {
        return isCold;
    }

    public void setIsCold(boolean cold) {
        isCold = cold;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}
