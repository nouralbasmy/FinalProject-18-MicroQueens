package com.example.restaurant.services;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getByCuisine(String cuisine) {
        return restaurantRepository.findByCuisineIgnoreCase(cuisine);
    }
}