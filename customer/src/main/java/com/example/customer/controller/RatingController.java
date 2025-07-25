package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.model.Rating;
import com.example.customer.services.CustomerService;
import com.example.customer.services.RatingService;
import com.example.customer.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private  RatingService ratingService;
    @Autowired
    private CustomerService customerService;




    @Autowired
    private JwtUtil jwtUtil;

    public RatingController(RatingService ratingService, CustomerService customerService, JwtUtil jwtUtil) {
        this.ratingService = ratingService;
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    // Add a new rating
    @PostMapping("/rate")
    public ResponseEntity<Rating> rateRestaurant(
            @RequestBody Rating rating,
            @RequestHeader("Authorization") String authHeader) {

        // Extract token from "Bearer <token>"
        String token = authHeader.replace("Bearer ", "");
        //String username = jwtUtil.validateTokenAndGetUsername(token);
        Map<String, String> userInfo = jwtUtil.validateToken(token);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userId = userInfo.get("userId");
        String username = userInfo.get("username");
        System.out.println("Received token: " + token);
        System.out.println("Extracted username: " + username);

        // If token is invalid or expired
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Customer customer = customerService.getCustomerByUsername(username);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Inject the current customer

        rating.setCustomer(customer);

        Rating saved = ratingService.addRating(rating);
        return ResponseEntity.ok(saved);
    }

    // Update an existing rating
    @PutMapping("/{ratingId}")
    public ResponseEntity<String> updateRating(@PathVariable Long ratingId,
                                               @RequestBody Map<String, Object> body) {
        int newScore = Integer.parseInt(body.get("score").toString());
        Rating updated = ratingService.updateRating(ratingId, newScore);

        if (updated != null) {
            return ResponseEntity.ok("Rating updated.");
        } else {
            return ResponseEntity.badRequest().body("Rating not found.");
        }
    }
}
