package com.example.restaurant.controller;

import com.example.restaurant.dto.RatingSummaryDTO;
import com.example.restaurant.dto.RestaurantDTO;
import com.example.restaurant.model.Restaurant;
import com.example.restaurant.services.RestaurantService;
import com.example.restaurant.strategy.CuisineFilterStrategy;
import com.example.restaurant.strategy.DietaryRestrictionFilterStrategy;
import com.example.restaurant.strategy.RestaurantFilterContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @GetMapping("/filter")
    public List<Restaurant> filterRestaurants(
            @RequestParam String type,
            @RequestParam String value) {

        RestaurantFilterContext context = new RestaurantFilterContext();

        switch (type.toLowerCase()) {
            case "dietary" -> context.setStrategy(new DietaryRestrictionFilterStrategy(restaurantService));
            case "cuisine" -> context.setStrategy(new CuisineFilterStrategy(restaurantService));
            default -> throw new IllegalArgumentException("Invalid filter type");
        }

        return context.filter(value);
    }

//    @GetMapping("/filterByDietary")
//    public List<Restaurant> getRestaurantsByDietaryRestriction(@RequestParam String restriction) {
//        return restaurantService.getRestaurantsByDietaryRestriction(restriction);
//    }
//    @GetMapping("/filterByCuisine")
//    public List<Restaurant> filterRestaurants(
//            @RequestParam String cuisine) {
//        return restaurantService.getRestaurantsByCuisine( cuisine);
//    }

    // Create a new restaurant
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            // Optional: Basic range check (0–23)
            if (restaurant.getOpenTime() < 0 || restaurant.getOpenTime() > 23 ||
                    restaurant.getCloseTime() < 0 || restaurant.getCloseTime() > 23) {
                return ResponseEntity.badRequest().body("Open and close hours must be between 0 and 23.");
            }

            Restaurant savedRestaurant = restaurantService.addRestaurant(restaurant);
            return ResponseEntity.ok("Restaurant created successfully with ID: " + savedRestaurant.getId());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create restaurant: " + e.getMessage());
        }
    }


    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Restaurant not found"));
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

    @DeleteMapping("/deleteByName/{name}")
    public ResponseEntity<String> deleteRestaurantByName(@PathVariable String name) {
        restaurantService.deleteRestaurantByName(name);
        return ResponseEntity.ok("Restaurant '" + name + "' deleted successfully");
    }

    @PostMapping("/{restaurantId}/rating-summary")
    public void updateRatingSummary(@PathVariable Long restaurantId,
                                    @RequestBody RatingSummaryDTO ratingSummaryDTO) {
        restaurantService.updateRatingSummary(restaurantId, ratingSummaryDTO);

        System.out.println("Received rating summary for restaurant ID: " + restaurantId);
        System.out.println("Average Score: " + ratingSummaryDTO.getAverageScore());
        System.out.println("Total Ratings: " + ratingSummaryDTO.getTotalRatings());
    }
    @PostMapping("/getRestaurantInfo")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByIds(@RequestBody List<Long> ids) {
        List<RestaurantDTO> restaurants = restaurantService.getRestaurantsByIds(ids);
        return ResponseEntity.ok(restaurants);
    }

    //added -N
    @GetMapping("/activeRestaurants")
    public ResponseEntity<List<RestaurantDTO>> getActiveRestaurants() {
        List<RestaurantDTO> restaurants = restaurantService.getActiveRestaurants();
        return ResponseEntity.ok(restaurants);
    }

}
