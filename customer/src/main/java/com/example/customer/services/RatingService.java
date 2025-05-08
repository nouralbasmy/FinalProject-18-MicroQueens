package com.example.customer.services;

import com.example.customer.clients.CustomerRatingClient;
import com.example.customer.dto.RatingSummaryDTO;
import com.example.customer.model.Rating;
import com.example.customer.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final CustomerRatingClient customerRatingClient;

    public RatingService(RatingRepository repo, CustomerRatingClient client) {
        this.ratingRepository = repo;
        this.customerRatingClient = client;
    }

    public Rating addRating(Rating rating) {
        Rating saved = ratingRepository.save(rating);
        updateRestaurantRatingSummary(saved.getRestaurantId());
        return saved;
    }

    public Rating updateRating(Long ratingId, int newScore) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);
        if (optionalRating.isPresent()) {
            Rating rating = optionalRating.get();
            rating.setScore(newScore);
            Rating updated = ratingRepository.save(rating);
            updateRestaurantRatingSummary(updated.getRestaurantId());
            return updated;
        }
        return null;
    }

    private void updateRestaurantRatingSummary(Long restaurantId) {
        List<Rating> allRatings = ratingRepository.findByRestaurantId(restaurantId);
        double average = allRatings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);

        long total = allRatings.size();
        RatingSummaryDTO summaryDTO = new RatingSummaryDTO(average, total);
        customerRatingClient.sendRating(restaurantId, summaryDTO);
    }
    public Rating rateRestaurant(Long customerId, Long restaurantId, int score) {
        Rating rating = new Rating();
        rating.setCustomerId(customerId);
        rating.setRestaurantId(restaurantId);
        rating.setScore(score);
        Rating saved = ratingRepository.save(rating);
        updateRestaurantRatingSummary(restaurantId);
        return saved;
    }
}
