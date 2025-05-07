package com.example.customer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "cart-service", url = "http://localhost:8090/cart")
public interface CartClient {
    @GetMapping("/myCart/{userId}")
    Map<String, Object> getMyCart(@PathVariable Long userId);
}
