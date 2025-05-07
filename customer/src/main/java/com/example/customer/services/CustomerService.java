package com.example.customer.services;

import com.example.customer.model.Customer;
import com.example.customer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    //------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
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

}
