package com.example.customer.services;

import com.example.customer.clients.RestaurantClient;
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
    private RestaurantClient restaurantClient;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }


    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer existing = optionalCustomer.get();
            existing.setUsername(updatedCustomer.getUsername());
            existing.setEmail(updatedCustomer.getEmail());
            existing.setFavouriteRestaurants(updatedCustomer.getFavouriteRestaurants());
            return customerRepository.save(existing);
        }
        return null;
    }


    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addToFavourites(Long customerId, Long restaurantId) {
        if (!restaurantClient.restaurantExists(restaurantId)) {
            return false;
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<Long> favourites = customer.getFavouriteRestaurants();
            if (favourites == null) favourites = new ArrayList<>();
            if (!favourites.contains(restaurantId)) {
                favourites.add(restaurantId);
                customer.setFavouriteRestaurants(favourites);
                customerRepository.save(customer);
                return true;
            }
        }
        return false;
    }



}

