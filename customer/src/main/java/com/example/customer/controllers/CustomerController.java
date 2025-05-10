package com.example.customer.controllers;

import com.example.customer.clients.CartClient;
import com.example.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    //------------FOR SYNC COMMUNICATION WITH PAYMENT MICROSERVICE----------------
    @PutMapping("/refund/{customerId}")
    public boolean refund(@PathVariable Long customerId, @RequestParam double amount)
    {
        return customerService.refund(customerId,amount);
    }
    //------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
    @GetMapping("/myCart/{userId}")
    public Map<String, Object> getMyCart(@PathVariable Long userId)
    {
        return customerService.getMyCart(userId);
    }

    //------------FOR SYNC COMMUNICATION WITH NOTIFICATION MICROSERVICE----------------
    //(1)
    @GetMapping("/phoneNumber/{userId}")
    public String getPhoneNumberById(@PathVariable Long userId)
    {
        return customerService.getPhoneNumberById(userId);
    }

    //(2)
    @GetMapping("/email/{userId}")
    public String getEmailById(@PathVariable Long userId)
    {
        return customerService.getEmailById(userId);
    }
}
