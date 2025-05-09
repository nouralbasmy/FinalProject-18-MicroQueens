package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.model.Rating;
import com.example.customer.services.CustomerService;
import com.example.customer.services.RatingService;
import com.example.customer.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private  RatingService ratingService;
    @Autowired
    private CustomerService customerService;


    @Autowired
    private JwtUtil jwtUtil;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Add a new rating
    @PostMapping("/rate")
    public ResponseEntity<Rating> rateRestaurant(
            @RequestBody Rating rating,
            @RequestHeader("Authorization") String authHeader) {

        // Extract token from "Bearer <token>"
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.validateTokenAndGetUsername(token);

        // If token is invalid or expired
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Customer customer = customerService.getCustomerByUsername(username);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Inject the current customer
        
        rating.setScore(rating.getScore());
        rating.setRestaurantId(rating.getRestaurantId());
        Customer customer2 = customerService.getCustomerById(rating.getCustomer().getId());
        rating.setCustomer(customer2);

        Rating saved = ratingService.addRating(rating);
        return ResponseEntity.ok(saved);
    }

    // Update an existing rating
    @PutMapping("/{ratingId}")
    public ResponseEntity<String> updateRating(@PathVariable Long ratingId,
                                               @RequestBody Map<String, Object> body) {
        int newScore = Integer.parseInt(body.get("score").toString());
        Rating updated = ratingService.updateRating(ratingId, newScore);

        if (updated != null) {
            return ResponseEntity.ok("Rating updated.");
        } else {
            return ResponseEntity.badRequest().body("Rating not found.");
        }
    }
}
