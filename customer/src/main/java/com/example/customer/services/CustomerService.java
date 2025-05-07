package com.example.customer.services;

import com.example.customer.model.Customer;
import com.example.customer.model.Rating;
import com.example.customer.repositories.CustomerRepository;
import com.example.customer.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.customer.utilities.JwtUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RatingRepository ratingRepository;

    // ✅ Add to Favourites
    public boolean addToFavourites(Long customerId, Long restaurantId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<Long> favourites = customer.getFavouriteRestaurants();
            if (favourites == null) favourites = new ArrayList<>();
            if (!favourites.contains(restaurantId)) {
                favourites.add(restaurantId);
                customer.setFavouriteRestaurants(favourites);
                customerRepository.save(customer); // Save update
                return true;
            }
        }
        return false;
    }

    // Rate Restaurant
    public boolean rateRestaurant(Long customerId, Long restaurantId, int score) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            Rating rating = new Rating(score, customer, restaurantId);
            ratingRepository.save(rating);
            return true;
        }
        return false;
    }

    public String authenticateAndGenerateToken(String username, String password) {
        Optional<Customer> customerOpt = customerRepository.findByUsername(username);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getPassword().equals(password)) {
                return JwtUtil.getInstance().generateToken(customer.getUsername());
            }
        }
        return null;
    }




}
