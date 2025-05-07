package com.example.restaurant.repository;

import com.example.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.menu m WHERE :restriction MEMBER OF m.dietaryRestrictions")
    List<Restaurant> findByMenuDietaryRestriction(@Param("restriction") String restriction);

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    // Find by name (exact match)
    Optional<Restaurant> findByName(String name);

    // Find active restaurants
    List<Restaurant> findByActiveTrue();

    // Custom update for activation status
    @Modifying
    @Transactional
    @Query("UPDATE Restaurant r SET r.active = :active WHERE r.id = :id")
    int updateActiveStatus(@Param("id") Long id, @Param("active") boolean active);

    // Custom delete by name
    @Modifying
    @Transactional
    void deleteByName(String name);

}