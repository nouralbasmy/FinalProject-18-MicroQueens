package com.example.order.controller;

import com.example.order.clients.CustomerClient;
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
    private final CustomerClient customerClient;


    @Autowired
    public CartController(CartService cartService, CustomerClient customerClient) {
        this.cartService = cartService;
        this.customerClient = customerClient;
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
    public Iterable<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @PutMapping("/user/updateCart")
    public Cart updateCartByUserId(@RequestHeader("Authorization") String authHeader, @RequestBody Cart updatedCartData) {

        Map<String, String> userInfo = customerClient.decodeToken(authHeader);
        if (userInfo == null || !userInfo.containsKey("userId")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        Long userId = Long.parseLong(userInfo.get("userId"));
        return cartService.updateCart(userId, updatedCartData);
    }

    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable String cartId) {
        cartService.deleteCart(cartId);
    }

    @DeleteMapping("/user/deleteCart")
    public void deleteCartByUserId(@RequestHeader("Authorization") String authHeader) {
        Map<String, String> userInfo = customerClient.decodeToken(authHeader);
        if (userInfo == null || !userInfo.containsKey("userId")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        Long userId = Long.parseLong(userInfo.get("userId"));
        cartService.deleteCartByUserId(userId);
    }

    // (1)
    @GetMapping("/user/getCart")
    public Cart getCartByUserId(@RequestHeader("Authorization") String authHeader) {
        Map<String, String> userInfo = customerClient.decodeToken(authHeader);
        if (userInfo == null || !userInfo.containsKey("userId")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        Long userId = Long.parseLong(userInfo.get("userId"));
        Optional<Cart> cart = cartService.getCartByUserId(userId);
        if (cart.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cart found for this user");
        }
        return cart.get();
    }

    // (2)
    @DeleteMapping("/user/removeItemFromCart")
    public String removeItemFromCart(@RequestHeader("Authorization") String authHeader, @RequestBody CartItem cartItem) {
        Map<String, String> userInfo = customerClient.decodeToken(authHeader);
        if (userInfo == null || !userInfo.containsKey("userId")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        Long userId = Long.parseLong(userInfo.get("userId"));
        boolean success = cartService.removeItemFromCart(userId, cartItem);
        if (success) {
            return "Item removed successfully!";
        }
        return "Failed to remove Item";
    }

    // (3)
    @PostMapping("/user/addItemToCart")
    public String addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CartItem cartItem) {
        try {
            Map<String, String> userInfo = customerClient.decodeToken(authHeader);
            if (userInfo == null || !userInfo.containsKey("userId")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            Long userId = Long.parseLong(userInfo.get("userId"));
            cartService.addToCart(userId, cartItem);
            return "Item added successfully!";
        } catch (Exception e) {
            return "Failed to add item to cart! " + e.getMessage();
        }
    }

    // (4)

    @PostMapping("/applyDiscount")
    public ResponseEntity<?> applyDiscount(@RequestParam String cartId, @RequestParam double discount) {
        try {
            Cart updatedCart = cartService.applyDiscount(cartId, discount);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //(5)
    @GetMapping("/myCart")
    public Map<String, Object> getMyCart(@RequestHeader("Authorization") String authHeader) {
        Map<String, String> userInfo = customerClient.decodeToken(authHeader);
        if (userInfo == null || !userInfo.containsKey("userId")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        Long userId = Long.parseLong(userInfo.get("userId"));

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

    // (6) explicitly checking inventory only -- for additional fn requirement
    @GetMapping("/checkInventory/{menuItemId}/{quantity}")
    public boolean checkInventory(@PathVariable Long menuItemId, @PathVariable int quantity) {
        return cartService.checkInventory(menuItemId, quantity);
    }
}
