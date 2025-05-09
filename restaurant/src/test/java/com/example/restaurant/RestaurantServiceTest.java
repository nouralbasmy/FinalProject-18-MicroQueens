package com.example.restaurant;
import com.example.restaurant.model.MenuItem;
import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    public void testUpdateRestaurantStatusByTime() {
        // Simulate the restaurant (before the time check)
        List<String> dietaryOptions = List.of("Vegetarian", "Vegan");
        List<MenuItem> menu = new ArrayList<>(); // Empty list or add some MenuItems for testing

        Restaurant restaurant = new Restaurant("Test Restaurant", "Italian", "123 Main St", "123-456-7890",
                4, true, dietaryOptions, menu);
        restaurant.setId(1L); // Assuming the restaurant has an ID field
        restaurant.setActive(false);  // Initially inactive

        // Mocking repository response for 'findAll' or 'findById'
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        // Simulate that the current time is within working hours (e.g., 12:00 PM)
        LocalTime currentTime = LocalTime.of(12, 0);  // 12:00 PM
        // Assuming your method internally checks time and sets active status
        restaurantService.updateRestaurantStatusByTime();

        // Verify if the restaurant is set to active (within working hours)
        verify(restaurantRepository).updateActiveStatus(restaurant.getId(), true);

        // Now test if restaurant is inactive outside working hours
        currentTime = LocalTime.of(23, 0);  // 11:00 PM
        restaurantService.updateRestaurantStatusByTime();

        // Verify if the restaurant is set to inactive (outside working hours)
        verify(restaurantRepository).updateActiveStatus(restaurant.getId(), false);
    }
}
