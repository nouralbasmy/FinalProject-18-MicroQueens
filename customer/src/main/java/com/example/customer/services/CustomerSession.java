package com.example.customer.services;

import com.example.customer.model.Customer;

public class CustomerSession {
    private static CustomerSession instance;
    private Customer currentCustomer;

    private CustomerSession() {} // private constructor

    public static synchronized CustomerSession getInstance() {
        if (instance == null) {
            instance = new CustomerSession();
        }
        return instance;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void login(Customer customer) {
        this.currentCustomer = customer;
    }

    public void logout() {
        this.currentCustomer = null;
    }

    public boolean isLoggedIn() {
        return currentCustomer != null;
    }
}

