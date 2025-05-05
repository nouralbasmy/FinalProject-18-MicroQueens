package com.example.customer.controllers;

import com.example.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Add to Favourites
    @PostMapping("/{customerId}/favourites")
    public ResponseEntity<String> addToFavourites(
            @PathVariable Long customerId,
            @RequestBody Map<String, Long> body) {

        Long restaurantId = body.get("restaurantId");
        boolean added = customerService.addToFavourites(customerId, restaurantId);

        return added
                ? ResponseEntity.ok("Added to favourites.")
                : ResponseEntity.badRequest().body("Failed to add to favourites.");
    }

    // Rate Restaurant
    @PostMapping("/{customerId}/rate")
    public ResponseEntity<String> rateRestaurant(
            @PathVariable Long customerId,
            @RequestBody Map<String, Object> body) {

        Long restaurantId = Long.valueOf(body.get("restaurantId").toString());
        int score = Integer.parseInt(body.get("score").toString());
        boolean success = customerService.rateRestaurant(customerId, restaurantId, score);

        return success
                ? ResponseEntity.ok("Rating submitted.")
                : ResponseEntity.badRequest().body("Failed to submit rating.");
    }
}
