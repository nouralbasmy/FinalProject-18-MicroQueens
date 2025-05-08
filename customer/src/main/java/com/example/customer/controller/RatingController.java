package com.example.customer.controller;

import com.example.customer.model.Rating;
import com.example.customer.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Add a new rating
    @PostMapping
    public ResponseEntity<Rating> rateRestaurant(@RequestBody Rating rating) {
        Rating saved = ratingService.addRating(rating);
        return ResponseEntity.ok(saved);
    }

    // Add a rating using restaurantId and score
    @PostMapping("/rate")
    public ResponseEntity<Rating> rateRestaurant(@RequestBody Map<String, Object> body) {
        Long customerId = Long.valueOf(body.get("customerId").toString());
        Long restaurantId = Long.valueOf(body.get("restaurantId").toString());
        int score = Integer.parseInt(body.get("score").toString());
        Rating rating = ratingService.rateRestaurant(customerId, restaurantId, score);
        return ResponseEntity.ok(rating);
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
