package com.example.restaurant.strategy;

import com.example.restaurant.model.Restaurant;
import java.util.List;

public interface RestaurantFilterStrategy {
    List<Restaurant> filter(String value);
}
