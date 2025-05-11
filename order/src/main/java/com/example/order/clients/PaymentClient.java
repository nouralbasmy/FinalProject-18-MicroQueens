package com.example.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service", url = "http://localhost:8091/payment")
public interface PaymentClient {
    @PostMapping("/pay")
    Long pay(@RequestHeader("Authorization") String authHeader, @RequestParam String paymentType, @RequestParam double amount, @RequestParam String extraInfo);

    @PutMapping("/updateOrderId/{paymentId}")
    void setPaymentOrderId(@PathVariable Long paymentId, @RequestParam Long orderId);
}
