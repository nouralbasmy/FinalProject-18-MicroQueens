package com.example.payment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @FeignClient(name = "customer-service", url = "http://localhost:8094/order")
@FeignClient(name = "customer-service", url = "${customer.url}")
public interface CustomerClient {
    @PutMapping("/refund/{customerId}")
    boolean refund(@PathVariable Long customerId, @RequestParam double amount);
}
