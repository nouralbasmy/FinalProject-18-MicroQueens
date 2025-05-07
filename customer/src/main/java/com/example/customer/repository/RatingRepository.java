package com.example.customer.repository;

import com.example.customer.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // It's good practice to add @Repository annotation
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRestaurantId(Long restaurantId);


    //Optional<Rating> findByCustomer_IdAndRestaurantId(Long customerId, Long restaurantId);

}
