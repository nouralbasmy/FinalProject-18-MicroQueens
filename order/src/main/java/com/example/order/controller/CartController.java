package com.example.order.controller;

import com.example.order.model.Cart;
import com.example.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.order.model.CartItem;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
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

    @GetMapping
    public Iterable<Cart> getAllCarts()
    {
        return cartService.getAllCarts();
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cart found for this user");
        }
        return cart.get();
    }

    // (2)
    @DeleteMapping("/user/{userId}/remove")
    public String removeItemFromCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        boolean success = cartService.removeItemFromCart(userId, cartItem);
        if (success) {
            return "Item removed successfully!";
        }
        return "Failed to remove Item";
    }

    // (3)
    @PostMapping("/user/{userId}/add")
    public String addToCart(
            @PathVariable Long userId,
            @RequestBody CartItem cartItem) {
        try
        {
            cartService.addToCart(userId, cartItem);
            return "Item added successfully!";
        }
        catch (Exception e)
        {
            return "Failed to add item to cart! "+ e.getMessage();
        }
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

    //(5)
    @GetMapping("/myCart/{userId}")
    public Map<String, Object> getMyCart(@PathVariable Long userId) {
        Optional<Cart> cart = cartService.getCartByUserId(userId);
        if (cart.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cart found for this user");
        }
        Map<String, Object> cartDTO = new HashMap<>();
        cartDTO.put("id", cart.get().getId());
        cartDTO.put("totalPrice", cart.get().getTotalPrice());
        cartDTO.put("items", cart.get().getCartItemList());
        return cartDTO;
    }
}
