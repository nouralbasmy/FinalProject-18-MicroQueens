package com.example.customer.controller;

import com.example.customer.dto.RestaurantDTO;
import com.example.customer.model.Customer;
import com.example.customer.services.CustomerService;
import com.example.customer.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtil jwtUtil;

    // Create Customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // Get Customer by ID
    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    //  Get All Customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //  Update Customer
    @PutMapping("/{customerId}")
    public Customer updateCustomer(
            @PathVariable Long customerId,
            @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(customerId, updatedCustomer);
    }

    //  Delete Customer
    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId) {
        boolean deleted = customerService.deleteCustomer(customerId);
        return deleted ? "Customer deleted." : "Customer not found.";
    }

    //  Add to Favourites
    @PostMapping("/addToFav")
    public ResponseEntity<String> addToFavourites(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Long> body) {

        String token = authHeader.replace("Bearer ", "");
        Map<String, String> userInfo = jwtUtil.validateToken(token);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }

        String userId = userInfo.get("userId");
        String username = userInfo.get("username");

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }

        Customer customer = customerService.getCustomerByUsername(username);
        Long restaurantId = body.get("restaurantId");

        boolean added = customerService.addToFavourites(customer.getId(), restaurantId);
        return added
                ? ResponseEntity.ok("Added to favourites.")
                : ResponseEntity.ok("Already in favourites.");
    }


    @GetMapping("/myFavourites")
    public ResponseEntity<?> getFavouriteRestaurants(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Map<String, String> userInfo = jwtUtil.validateToken(token);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }

        String userId = userInfo.get("userId");
        String username = userInfo.get("username");

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }

        Customer customer = customerService.getCustomerByUsername(username);
        List<Long> restaurantIds = customer.getFavouriteRestaurantIds();

        // Call Restaurant service to get full info
        List<RestaurantDTO> favouriteRestaurants = customerService.getFavouriteRestaurantsInfo(restaurantIds);

        return ResponseEntity.ok(favouriteRestaurants);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        String token = customerService.authenticateAndGenerateToken(username, password);
        if (token != null) {
            return ResponseEntity.ok("Bearer " + token);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password.");
        }
    }


    //------------FOR SYNC COMMUNICATION WITH PAYMENT MICROSERVICE----------------
    @PutMapping("/refund/{customerId}")
    public boolean refund(@PathVariable Long customerId, @RequestParam double amount) {
        return customerService.refund(customerId, amount);
    }

    //------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
    @GetMapping("/myCart/{userId}")
    public Map<String, Object> getMyCart(@PathVariable Long userId) {
        return customerService.getMyCart(userId);
    }

    //------------FOR SYNC COMMUNICATION WITH NOTIFICATION MICROSERVICE----------------
    //(1)
    @GetMapping("/phoneNumber/{userId}")
    public String getPhoneNumberById(@PathVariable Long userId) {
        return customerService.getPhoneNumberById(userId);
    }

    //(2)
    @GetMapping("/email/{userId}")
    public String getEmailById(@PathVariable Long userId) {
        return customerService.getEmailById(userId);
    }


    //------------FOR COMMUNICATION WITH ORDER & NOTIFICATION MICROSERVICE----------------
    @PutMapping("/markOrder/{userId}")
    public String markOrderDelivered(@PathVariable Long userId, @RequestParam Long orderId) {
        return customerService.markOrderDelivered(userId, orderId);
    }

}
