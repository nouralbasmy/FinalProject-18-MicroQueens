package com.example.restaurant.repository;

import com.example.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Filter by cuisine
    List<Restaurant> findByCuisineIgnoreCase(String cuisine);

//    // Optional: Search by name
//    List<Restaurant> findByNameContainingIgnoreCase(String name);


}
