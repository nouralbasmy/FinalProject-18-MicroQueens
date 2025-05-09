package com.example.restaurant.services;

import com.example.restaurant.dto.RatingSummaryDTO;
import com.example.restaurant.enums.Cuisine;
import com.example.restaurant.enums.DietaryOption;
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

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }



    @CachePut(value = "restaurant_cache", key = "#result.id")
    public Restaurant addRestaurant(Restaurant restaurant) {
        int now = LocalTime.now().getHour();
        int open = restaurant.getOpenTime();
        int close = restaurant.getCloseTime();

        boolean isActive = (open < close)
                ? now >= open && now < close
                : now >= open || now < close;

        restaurant.setActive(isActive);

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
                    restaurant.setCuisines(updatedRestaurant.getCuisines());
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
    public List<Restaurant> getRestaurantsByCuisine(Cuisine cuisine) {
        return restaurantRepository.findByCuisinesContaining(cuisine);
    }
    @Cacheable(value = "restaurant_cache", key = "#restriction")
    public List<Restaurant> getRestaurantsByDietaryRestriction(DietaryOption restriction) {
        return restaurantRepository.findByDietaryOptionsContaining(restriction);
    }


    @Scheduled(cron = "0 0 * * * *")  // This cron expression runs the method every hour
    public void updateRestaurantStatusByTime() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        int now = LocalTime.now().getHour();
        // Loop through all restaurants and update their status
        for (Restaurant restaurant : restaurants) {
            int open = restaurant.getOpenTime();
            int close = restaurant.getCloseTime();
            boolean isActive;
            if (open < close) {
                // Same-day hours (e.g., 9 to 22)
                isActive = now >= open && now < close;
            } else {
                // Overnight hours (e.g., 20 to 4)
                isActive = now >= open || now < close;
            }

            // Update active status in DB
            restaurantRepository.updateActiveStatus(restaurant.getId(), isActive);
        }
    }


}