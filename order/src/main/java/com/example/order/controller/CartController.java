package com.example.order.controller;

import com.example.order.model.Cart;
import com.example.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/applyDiscount")
    public ResponseEntity<Cart> applyDiscount(@RequestParam String cartId, @RequestParam double discount) {
        try {
            Cart updatedCart = cartService.applyDiscount(cartId, discount);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
