package com.example.restaurant.services;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;


import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable(value = "restaurant_cache", key = "#restriction")
    public List<Restaurant> getRestaurantsByDietaryRestriction(String restriction) {
        return restaurantRepository.findByMenuDietaryRestriction(restriction);
    }

    @CachePut(value = "restaurant_cache", key = "#result.id")
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Cacheable(value = "restaurant_cache", key = "#id")
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    @CachePut(value = "restaurant_cache", key = "#id")
    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurant.setName(updatedRestaurant.getName());
                    restaurant.setCuisine(updatedRestaurant.getCuisine());
                    restaurant.setAddress(updatedRestaurant.getAddress());
                    restaurant.setPhone(updatedRestaurant.getPhone());
                    restaurant.setRating(updatedRestaurant.getRating());
                    restaurant.setActive(updatedRestaurant.isActive());
                    restaurant.setDietaryOptions(updatedRestaurant.getDietaryOptions());
                    return restaurantRepository.save(restaurant);
                })
                .orElseGet(() -> {
                    updatedRestaurant.setId(id);
                    return restaurantRepository.save(updatedRestaurant);
                });
    }

    @CachePut(value = "restaurant_cache", key = "#id")
    public Restaurant updateRestaurantStatus(Long id, boolean active) {
        restaurantRepository.updateActiveStatus(id, active);
        return restaurantRepository.findById(id).orElseThrow();
    }

    @CacheEvict(value = "restaurant_cache", key = "#id")
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);

    }

    @CacheEvict(value = "restaurant_cache", key = "#name")
    public void deleteRestaurantByName(String name) {
        restaurantRepository.deleteByName(name);
    }

    public List<Restaurant> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }


    @Cacheable(value = "restaurant_cache", key = "#cuisine")
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisine(cuisine);
    }

}