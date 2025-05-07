package com.example.restaurant.services.strategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.example.restaurant.model.Restaurant;
@Component
public class InMemoryCacheStrategy implements CacheStrategy {
    private final Map<Long, Restaurant> cache = new HashMap<>();

    public Optional<Restaurant> getRestaurantById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    public void saveRestaurant(Restaurant restaurant) {
        cache.put(restaurant.getId(), restaurant);
    }
}
