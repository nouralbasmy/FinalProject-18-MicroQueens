package com.example.order.controller;

import com.example.order.model.Cart;
import com.example.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.order.model.CartItem;
import com.example.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart addCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }

    @GetMapping("/{cartId}")
    public Optional<Cart> getCartById(@PathVariable String cartId) {
        return cartService.getCartById(cartId);
    }

    @PutMapping("/user/{userId}")
    public Cart updateCartByUserId(@PathVariable Long userId, @RequestBody Cart updatedCartData) {
        return cartService.updateCart(userId, updatedCartData);
    }

    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable String cartId) {
        cartService.deleteCart(cartId);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteCartByUserId(@PathVariable Long userId) {
        cartService.deleteCartByUserId(userId);
    }

    // (1)
    @GetMapping("/user/{userId}")
    public Cart getCartByUserId(@PathVariable Long userId) {
        Optional<Cart> cart = cartService.getCartByUserId(userId);
        if (cart.isEmpty()) {
            throw new RuntimeException("No cart found for this user");
        }
        return cart.get();
    }

    // (2)
    @DeleteMapping("/user/{userId}/remove")
    public String removeFromCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        boolean success = cartService.removeFromCart(userId, cartItem);
        if (success) {
            return "Item removed successfully!";
        }
        return "Failed to remove Item";
    }

    // (3)
    @PostMapping("/{userId}/add")
    public void addToCart(
            @PathVariable Long userId,
            @RequestBody CartItem cartItem) {
        cartService.addToCart(userId, cartItem);
    }

    // (4)

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
