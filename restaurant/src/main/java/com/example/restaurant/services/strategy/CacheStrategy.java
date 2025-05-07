package com.example.restaurant.services.strategy;
import com.example.restaurant.model.Restaurant;
import java.util.Optional;

public interface CacheStrategy {
    Optional<Restaurant> getRestaurantById(Long id);
    void saveRestaurant(Restaurant restaurant);
}