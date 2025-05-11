package com.example.payment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "customer-service", url = "http://localhost:8094/customer")
public interface CustomerClient {
    @PutMapping("/refund/{customerId}")
    boolean refund(@PathVariable Long customerId, @RequestParam double amount);

    @GetMapping("/validateToken")
    Map<String, String> decodeToken(@RequestHeader("Authorization") String authHeader);
}
