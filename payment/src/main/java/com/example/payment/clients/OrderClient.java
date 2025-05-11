package com.example.payment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// @FeignClient(name = "order-service", url = "http://localhost:8090/order")
@FeignClient(name = "order-service", url = "${order.url}")
public interface OrderClient {
    @PutMapping("/refundStatus/{orderId}")
    void refundStatus(@PathVariable Long orderId);
}
