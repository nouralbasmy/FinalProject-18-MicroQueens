package com.example.order.service;

import com.example.order.model.Cart;
import com.example.order.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository)
    {
        this.cartRepository = cartRepository;
    }

    //(1) get cart by userID
    public Optional<Cart> getCartByUserId(Long userId)
    {
        return cartRepository.findByUserId(userId);
    }

    public void deleteCart(String cartId) {
        cartRepository.deleteById(cartId);
    }

    public Cart applyDiscount(String cartId, double discountPercent) {

        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }

        Cart cart = optionalCart.get();

        double total = cart.getTotalPrice();
        double discountAmount = total * (discountPercent / 100.0);
        double newTotal = total - discountAmount;

        cart.setTotalPrice(newTotal);
        cartRepository.save(cart);

        return cart;
    }
}