package com.example.customer;


import com.example.customer.model.Customer;
import com.example.customer.model.Rating;
import com.example.customer.services.RatingService;
import com.example.customer.utilities.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest

public class TestFile {

    @Autowired
    private JwtUtil jwtUtil;


    //    @Test
//    public void testGenerateAndValidateToken() {
//        // Given
//        String username = "jake_brown";
//
//        // When
//        String token = jwtUtil.generateToken(username);
//
//        // Then
//        assertNotNull(token, "Token should not be null");
//        String extractedUsername = jwtUtil.validateTokenAndGetUsername(token);
//        assertEquals(username, extractedUsername, "Extracted username should match the original username");
//    }
    @Test
    public void testGenerateAndValidateToken() {
        // Given
        Long userId = 1L;
        String username = "jake_brown";

        // When
        String token = jwtUtil.generateToken(userId, username);

        // Then
        assertNotNull(token, "Token should not be null");

        // Validate token and extract the username and userId
        Map<String, String> userInfo = jwtUtil.validateToken(token);
        assertNotNull(userInfo, "Token should contain user information");

        String extractedUsername = userInfo.get("username");
        String extractedUserId = userInfo.get("userId");

        // Assert the username and userId match the expected values
        assertEquals(username, extractedUsername, "Extracted username should match the original username");
        assertEquals(userId.toString(), extractedUserId, "Extracted userId should match the original userId");
    }

    @Test
    public void testSingletonBehavior() {
        JwtUtil instance1 = jwtUtil;
        JwtUtil instance2 = jwtUtil;
        assertSame(instance1, instance2, "JwtUtil should behave as a singleton");
    }

    @Test
    public void testCustomerBuilderPattern() {
        // Given
        Long id = 1L;
        String username = "jake_brown";
        String email = "jake_brown@example.com";
        String password = "secret";
        List<Long> favouriteRestaurantIds = Arrays.asList(101L, 102L);
        List<Rating> ratings = Arrays.asList(); // Empty for simplicity

        // When
        Customer customer = new Customer.Builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .favouriteRestaurantIds(favouriteRestaurantIds)
                .ratings(ratings)
                .build();

        // Then
        assertNotNull(customer, "Customer should not be null");
        assertEquals(id, customer.getId(), "ID should match");
        assertEquals(username, customer.getUsername(), "Username should match");
        assertEquals(email, customer.getEmail(), "Email should match");
        assertEquals(password, customer.getPassword(), "Password should match");
        assertEquals(favouriteRestaurantIds, customer.getFavouriteRestaurantIds(), "Favourite restaurant IDs should match");
        assertEquals(ratings, customer.getRatings(), "Ratings should match");
    }

    // Testing rate a restaurant


}
