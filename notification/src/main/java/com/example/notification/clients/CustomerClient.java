package com.example.notification.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "customer-service", url = "http://localhost:8094/order")
@FeignClient(name = "customer-service", url = "${customer.url}")
public interface CustomerClient {
    @GetMapping("/phoneNumber/{userId}")
    String getPhoneNumberById(@PathVariable Long userId);


    @GetMapping("/email/{userId}")
    String getEmailById(@PathVariable Long userId);
}
