package com.example.restaurant.strategy;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.services.RestaurantService;
import java.util.List;

public class DietaryRestrictionFilterStrategy implements RestaurantFilterStrategy {

    private final RestaurantService restaurantService;

    public DietaryRestrictionFilterStrategy(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public List<Restaurant> filter(String restriction) {
        return restaurantService.getRestaurantsByDietaryRestriction(restriction);
    }
}
