package com.example.customer.services;

import com.example.customer.clients.CartClient;
import com.example.customer.model.Customer;
import com.example.customer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CartClient cartClient;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CartClient cartClient)
    {
        this.customerRepository = customerRepository;
        this.cartClient = cartClient;
    }

    //------------FOR SYNC COMMUNICATION WITH PAYMENT MICROSERVICE----------------
    // refund
    public boolean refund(Long customerId, double refundAmount)
    {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty())
            return false;
        Customer customer = optionalCustomer.get();
        customer.setWallet(customer.getWallet()+refundAmount);
        customerRepository.save(customer);
        return true;
    }

    //------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
    public Map<String, Object> getMyCart(Long userId)
    {
        return cartClient.getMyCart(userId);
    }

    //------------FOR SYNC COMMUNICATION WITH NOTIFICATION MICROSERVICE----------------
    //(1) get phone number by id
    public String getPhoneNumberById(Long userId)
    {
        Optional<Customer> optionalCustomer = customerRepository.findById(userId);
        if(optionalCustomer.isEmpty())
            return null;
        return optionalCustomer.get().getPhoneNumber();
    }

    //(2) get email by id
    public String getEmailById(Long userId)
    {
        Optional<Customer> optionalCustomer = customerRepository.findById(userId);
        if(optionalCustomer.isEmpty())
            return null;
        return optionalCustomer.get().getEmail();
    }

}
