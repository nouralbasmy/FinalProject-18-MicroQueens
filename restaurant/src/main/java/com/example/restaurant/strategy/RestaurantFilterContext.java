package com.example.restaurant.strategy;


import com.example.restaurant.model.Restaurant;
import java.util.List;

public class RestaurantFilterContext {

    private RestaurantFilterStrategy strategy;

    // Set the strategy dynamically
    public void setStrategy(RestaurantFilterStrategy strategy) {
        this.strategy = strategy;
    }

    // Execute the filter strategy
    public List<Restaurant> filter(String value) {
        if (strategy == null) {
            throw new IllegalStateException("No strategy set.");
        }
        return strategy.filter(value);
    }
}
