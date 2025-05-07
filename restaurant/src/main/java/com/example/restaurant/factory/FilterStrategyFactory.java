package com.example.restaurant.factory;

import com.example.restaurant.services.RestaurantService;
import com.example.restaurant.strategy.*;

public class FilterStrategyFactory {

    private final RestaurantService restaurantService;

    public FilterStrategyFactory(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public RestaurantFilterStrategy getStrategy(String type) {
        return switch (type.toLowerCase()) {
            case "cuisine" -> new CuisineFilterStrategy(restaurantService);
            case "dietary" -> new DietaryRestrictionFilterStrategy(restaurantService);
            default -> throw new IllegalArgumentException("Unknown filter type: " + type);
        };
    }
}