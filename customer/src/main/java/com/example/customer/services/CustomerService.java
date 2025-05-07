package com.example.customer.services;

import com.example.customer.model.Customer;
import com.example.customer.model.Rating;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private RatingService ratingService;

    //  Create Customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    //  Read Customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    //  Read All Customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //  Update Customer
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer existing = optionalCustomer.get();
            // You can customize which fields to update
            existing.setUsername(updatedCustomer.getUsername());
            existing.setEmail(updatedCustomer.getEmail());
            existing.setFavouriteRestaurants(updatedCustomer.getFavouriteRestaurants());
            return customerRepository.save(existing);
        }
        return null;
    }

    //  Delete Customer
    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //  Add to Favourites
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
            ratingService.addRating(rating);
            return true;
        }
        return false;
    }
}

