package com.example.order.repository;

import com.example.order.model.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, String> {
    Optional<Cart> findByUserId(Long userId);
}
