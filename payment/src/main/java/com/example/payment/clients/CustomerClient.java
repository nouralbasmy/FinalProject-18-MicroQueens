package com.example.payment.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface CustomerClient {
    @PutMapping("/refund/{customerId}")
    boolean refund(@PathVariable Long customerId, @RequestParam double amount);
}
