package com.example.payment.repository;

import com.example.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByUserId(Long userId);

    Payment findByOrderId(Long orderId);
}
