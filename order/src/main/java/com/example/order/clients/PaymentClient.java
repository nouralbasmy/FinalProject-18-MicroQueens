package com.example.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", url = "http://localhost:8091/payment")
public interface PaymentClient {
    @PostMapping("/pay")
    Long pay(@RequestParam String paymentType, @RequestParam double amount, @RequestParam Long orderId, @RequestParam Long userId, @RequestParam String extraInfo);

    @PutMapping("/updateOrderId/{paymentId}")
    void setPaymentOrderId(@PathVariable Long paymentId, @RequestParam Long orderId);
}
