package com.example.restaurant.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SIDE_DISH")
public class SideDish extends MenuItem {
    private String size; // small, medium, large
    private String dip;  // ketchup, mayo
    private boolean isSpicy;

    public SideDish() {}

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDip() {
        return dip;
    }

    public void setDip(String dip) {
        this.dip = dip;
    }

    public boolean isSpicy() {
        return isSpicy;
    }

    public void setIsSpicy(boolean spicy) {
        isSpicy = spicy;
    }
}
