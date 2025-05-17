package com.example.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

//@FeignClient(name = "customer-service", url = "http://localhost:8094/customer")
@FeignClient(name = "customer-service", url = "${customer.url}")
public interface CustomerClient {
    @GetMapping("/validateToken")
    Map<String, String> decodeToken(@RequestHeader("Authorization") String authHeader);
}
