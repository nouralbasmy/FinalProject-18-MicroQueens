package com.example.restaurant.services;

import com.example.restaurant.dto.RatingSummaryDTO;
import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    //private final CustomerRatingClient customerRatingClient;
    private final RestaurantRepository restaurantRepository;
    private static final LocalTime OPEN_TIME = LocalTime.of(9, 0);  // 9:00 AM
    private static final LocalTime CLOSE_TIME = LocalTime.of(21, 0);  // 9:00 PM

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

    @Cacheable(value = "restaurant_cache", key = "#name")
    public List<Restaurant> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }


    @CachePut(value = "restaurant_cache", key = "#id")
    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurant.setName(updatedRestaurant.getName());
                    restaurant.setCuisine(updatedRestaurant.getCuisine());
                    restaurant.setAddress(updatedRestaurant.getAddress());
                    restaurant.setPhone(updatedRestaurant.getPhone());
                    restaurant.setAvgRating(updatedRestaurant.getAvgRating());
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

    //  update the restaurant's rating
    @CachePut(value = "restaurant_cache", key = "#restaurantId")
    public void updateRatingSummary(Long restaurantId, RatingSummaryDTO summaryDTO) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setAvgRating(summaryDTO.getAverageScore());
        restaurant.setTotalRatings(summaryDTO.getTotalRatings());
        restaurantRepository.save(restaurant);
    }

    @Cacheable(value = "restaurant_cache", key = "#cuisine")
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisine(cuisine);
    }

    @Scheduled(cron = "0 * * * * *")  // This cron expression runs the method every minute
    public void updateRestaurantStatusByTime() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        // Loop through all restaurants and update their status
        for (Restaurant restaurant : restaurants) {
            LocalTime currentTime = LocalTime.now();
            boolean isActive = currentTime.isAfter(OPEN_TIME) && currentTime.isBefore(CLOSE_TIME);

            // Update the restaurant's status
            restaurantRepository.updateActiveStatus(restaurant.getId(), isActive);
        }
    }
}