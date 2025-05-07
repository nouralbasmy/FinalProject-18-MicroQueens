package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

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
    @PostMapping("/{customerId}/favourites")
    public String addToFavourites(
            @PathVariable Long customerId,
            @RequestBody Map<String, Long> body) {

        Long restaurantId = body.get("restaurantId");
        boolean added = customerService.addToFavourites(customerId, restaurantId);
        return added ? "Added to favourites." : "Failed to add to favourites.";
    }


}
