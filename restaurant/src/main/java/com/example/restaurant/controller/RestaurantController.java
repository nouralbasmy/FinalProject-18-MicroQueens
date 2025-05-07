package com.example.restaurant.controller;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/cuisine/{cuisine}")
    public List<Restaurant> getByCuisine(@PathVariable String cuisine) {
        return restaurantService.getByCuisine(cuisine);
    }
}