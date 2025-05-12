package com.example.customer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

// @FeignClient(name = "cart-service", url = "http://localhost:8090/cart")
@FeignClient(name = "cart-service", url = "${cart.url}")
public interface CartClient {
    @GetMapping("/myCart")
    public Map<String, Object> getMyCart(@RequestHeader("Authorization") String authHeader);
}
