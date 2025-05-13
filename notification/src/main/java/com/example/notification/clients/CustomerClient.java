package com.example.notification.clients;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

// @FeignClient(name = "customer-service", url = "http://localhost:8094/order")
@FeignClient(name = "customer-service", url = "${customer.url}")
public interface CustomerClient {
    @GetMapping("/phoneNumber/{userId}")
    String getPhoneNumberById(@PathVariable Long userId);

    @GetMapping("/email/{userId}")
    String getEmailById(@PathVariable Long userId);

    @GetMapping("/validateToken")
    Map<String, String> decodeToken(@RequestHeader("Authorization") String authHeader);
}
