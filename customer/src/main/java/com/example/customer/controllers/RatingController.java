package com.example.customer.controllers;

import com.example.customer.model.Rating;
import com.example.customer.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public Rating create(@RequestBody Rating rating) {
        return ratingService.createRating(rating);
    }

    @GetMapping
    public List<Rating> getAll() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public Rating getById(@PathVariable Long id) {
        return ratingService.getRatingById(id);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Rating> getByRestaurant(@PathVariable Long restaurantId) {
        return ratingService.getRatingsForRestaurant(restaurantId);
    }

    @PutMapping("/{id}")
    public Rating update(@PathVariable Long id, @RequestBody Rating rating) {
        return ratingService.updateRating(id, rating);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean deleted = ratingService.deleteRating(id);
        return deleted ? "Rating deleted." : "Rating not found.";
    }
}

