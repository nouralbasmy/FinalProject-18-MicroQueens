package com.example.restaurant.strategy;

import com.example.restaurant.enums.Cuisine;
import com.example.restaurant.model.Restaurant;
import com.example.restaurant.services.RestaurantService;
import java.util.List;

public class CuisineFilterStrategy implements RestaurantFilterStrategy {

    private final RestaurantService restaurantService;

    public CuisineFilterStrategy(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public List<Restaurant> filter(String cuisine) {
        return restaurantService.getRestaurantsByCuisine(Cuisine.valueOf(cuisine));
    }
}