package com.example.customer.services;

import com.example.customer.clients.CartClient;
import com.example.customer.clients.NotificationClient;
import com.example.customer.clients.OrderClient;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;
import com.example.customer.clients.RestaurantClient;
import com.example.customer.dto.RestaurantDTO;
import com.example.customer.model.Rating;
import com.example.customer.repository.RatingRepository;
import com.example.customer.utilities.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    private RestaurantClient restaurantClient;

    private final CartClient cartClient;
    private final OrderClient orderClient;
    private final NotificationClient notificationClient;

    @Autowired
    public CustomerService(CartClient cartClient, OrderClient orderClient, NotificationClient notificationClient) {
        this.cartClient = cartClient;
        this.orderClient =orderClient;
        this.notificationClient = notificationClient;
    }

    // Create Customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Read Customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Read All Customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Update Customer
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer existing = optionalCustomer.get();
            // You can customize which fields to update
            existing.setUsername(updatedCustomer.getUsername());
            existing.setEmail(updatedCustomer.getEmail());
            existing.setFavouriteRestaurantIds(updatedCustomer.getFavouriteRestaurantIds());
            return customerRepository.save(existing);
        }
        return null;
    }

    // Delete Customer
    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add to Favourites
    @Transactional
    public boolean addToFavourites(Long customerId, Long restaurantId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<Long> favourites = customer.getFavouriteRestaurantIds();
            if (favourites == null)
                favourites = new ArrayList<>();
            if (!favourites.contains(restaurantId)) {
                favourites.add(restaurantId);
                customer.setFavouriteRestaurantIds(favourites);
                customerRepository.save(customer); // Save update
                return true;
            }
        }
        return false;
    }

    public List<RestaurantDTO> getFavouriteRestaurantsInfo(List<Long> restaurantIds) {
        if (restaurantIds == null || restaurantIds.isEmpty()) {
            return Collections.emptyList();
        }
        return restaurantClient.getRestaurantsByIds(restaurantIds);
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

    public String authenticateAndGenerateToken(String username, String password) {
        Optional<Customer> customerOpt = customerRepository.findByUsername(username);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getPassword().equals(password)) {
//                return JwtUtil.getInstance().generateToken(customer.getUsername());
                return JwtUtil.getInstance().generateToken(customer.getId(),customer.getUsername());
            }
        }
        return null;
    }

    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }

    // ------------FOR SYNC COMMUNICATION WITH PAYMENT MICROSERVICE----------------
    // refund
    public boolean refund(Long customerId, double refundAmount) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty())
            return false;
        Customer customer = optionalCustomer.get();
        customer.setWallet(customer.getWallet() + refundAmount);
        customerRepository.save(customer);
        return true;
    }


    // ------------FOR SYNC COMMUNICATION WITH NOTIFICATION
    // MICROSERVICE----------------
    // (1) get phone number by id
    public String getPhoneNumberById(Long userId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(userId);
        if (optionalCustomer.isEmpty())
            return null;
        return optionalCustomer.get().getPhoneNumber();
    }

    // (2) get email by id
    public String getEmailById(Long userId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(userId);
        if (optionalCustomer.isEmpty())
            return null;
        return optionalCustomer.get().getEmail();
    }

    //------------FOR COMMUNICATION WITH ORDER & NOTIFICATION MICROSERVICE----------------
    public String markOrderDelivered(String authHeader, Long orderId)
    {
        Long restaurantId = orderClient.getRestaurantIdByOrderId(orderId);
        orderClient.customerUpdateOrder(orderId);
        return notificationClient.addNotification(authHeader,restaurantId,orderId,"sms");
    }
}
