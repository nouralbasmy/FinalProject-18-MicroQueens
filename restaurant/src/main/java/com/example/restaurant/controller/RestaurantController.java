package com.example.restaurant.controller;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/filter")
    public List<Restaurant> getRestaurantsByDietaryRestriction(@RequestParam String restriction) {
        return restaurantService.getRestaurantsByDietaryRestriction(restriction);
    }

    // Create a new restaurant
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.addRestaurant(restaurant);
        return "Restaurant created successfully with ID: " + savedRestaurant.getId();
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public String getRestaurantById(@PathVariable Long id) {
        restaurantService.getRestaurantById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Restaurant not found"));
        return "Restaurant exists";
    }

    @PutMapping("/{id}")
    public String updateRestaurant(
            @PathVariable Long id,
            @RequestBody Restaurant restaurantDetails) {
        restaurantService.updateRestaurant(id, restaurantDetails);
        return "Restaurant updated successfully";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok("Restaurant deleted successfully");
    }

    // Search by name
    @GetMapping("/search")
    public List<Restaurant> searchByName(
            @RequestParam String name) {
        return restaurantService.searchRestaurantsByName(name);
    }

    @PatchMapping("/{id}/status")
    public String updateRestaurantStatus(@PathVariable Long id, @RequestParam boolean active) {
        restaurantService.updateRestaurantStatus(id, active);
        return "Restaurant status updated successfully";
    }


}
