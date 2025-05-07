package com.example.customer.controllers;

import com.example.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
    @PutMapping("/refund/{customerId}")
    public boolean refund(@PathVariable Long customerId, @RequestParam double amount)
    {
        return customerService.refund(customerId,amount);
    }
}
