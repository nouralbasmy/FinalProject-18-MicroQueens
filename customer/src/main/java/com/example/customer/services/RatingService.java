package com.example.customer.services;

import com.example.customer.model.Rating;
import com.example.customer.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    // Create Rating
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // Get all Ratings
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    // Get Rating by ID
    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    // Get Ratings for a specific Restaurant
    public List<Rating> getRatingsForRestaurant(Long restaurantId) {
        return ratingRepository.findByRestaurantId(restaurantId);
    }

    // Update Rating
    public Rating updateRating(Long id, Rating updatedRating) {
        Optional<Rating> optional = ratingRepository.findById(id);
        if (optional.isPresent()) {
            Rating existing = optional.get();
            existing.setScore(updatedRating.getScore());
            return ratingRepository.save(existing);
        }
        return null;
    }

    // Delete Rating
    public boolean deleteRating(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

