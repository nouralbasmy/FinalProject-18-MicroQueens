package com.example.restaurant;

import com.example.restaurant.model.Restaurant;
import com.example.restaurant.services.RestaurantService;
import com.example.restaurant.strategy.CuisineFilterStrategy;
import com.example.restaurant.strategy.DietaryRestrictionFilterStrategy;
import com.example.restaurant.strategy.RestaurantFilterContext;
import com.example.restaurant.strategy.RestaurantFilterStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StrategyPatternTest {
    @Test
    public void testCuisineFilter() {
        RestaurantService mockService = Mockito.mock(RestaurantService.class);
        List<Restaurant> mockRestaurants = List.of(new Restaurant("Italian Bistro", "Italian", "123 Street", "123456", 4, true, List.of(), List.of()));
        Mockito.when(mockService.getRestaurantsByCuisine("Italian")).thenReturn(mockRestaurants);

        RestaurantFilterStrategy strategy = new CuisineFilterStrategy(mockService);
        RestaurantFilterContext context = new RestaurantFilterContext();
        context.setStrategy(strategy);

        List<Restaurant> result = context.filter("Italian");
        assertEquals(1, result.size());
        assertEquals("Italian Bistro", result.get(0).getName());
    }
    @Test
    public void testCuisineFilterWithNoResults() {
        RestaurantService mockService = Mockito.mock(RestaurantService.class);
        Mockito.when(mockService.getRestaurantsByCuisine("Martian")).thenReturn(List.of());

        RestaurantFilterStrategy strategy = new CuisineFilterStrategy(mockService);
        RestaurantFilterContext context = new RestaurantFilterContext();
        context.setStrategy(strategy);

        List<Restaurant> result = context.filter("Martian");
        assertTrue(result.isEmpty(), "Expected no restaurants for Martian cuisine");
    }


    @Test
    public void testDietaryRestrictionFilterWithMultipleResults() {
        RestaurantService mockService = Mockito.mock(RestaurantService.class);
        List<Restaurant> mockRestaurants = List.of(
                new Restaurant("Healthy Haven", "Fusion", "123 Fit St", "111222", 5, true, List.of("gluten-free"), List.of()),
                new Restaurant("Green Bowl", "Vegan", "456 Green Rd", "333444", 4, true, List.of("gluten-free"), List.of())
        );

        Mockito.when(mockService.getRestaurantsByDietaryRestriction("gluten-free")).thenReturn(mockRestaurants);

        RestaurantFilterStrategy strategy = new DietaryRestrictionFilterStrategy(mockService);
        RestaurantFilterContext context = new RestaurantFilterContext();
        context.setStrategy(strategy);

        List<Restaurant> result = context.filter("gluten-free");
        assertEquals(2, result.size());
        assertEquals("Healthy Haven", result.get(0).getName());
        assertEquals("Green Bowl", result.get(1).getName());
    }


}