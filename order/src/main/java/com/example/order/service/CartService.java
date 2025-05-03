package com.example.order.service;

import com.example.order.model.Cart;
import com.example.order.model.CartItem;
import com.example.order.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    //Basic CRUD operation
    //CREATE "C"
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    //GET cart by id "R"
    public Optional<Cart> getCartById(String cartId) {
        return cartRepository.findById(cartId);
    }

    //UPDATE "U"
    public Cart updateCart(Long userId, Cart updatedCartData) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isEmpty()) throw new RuntimeException("No cart found for this user id");
        Cart existingCart = cart.get();
//        existingCart.setUserId(updatedCartData.getUserId());
        existingCart.setTotalPrice(updatedCartData.getTotalPrice());
        existingCart.setCartItemList(updatedCartData.getCartItemList());
        return cartRepository.save(existingCart);
    }

    // DELETE "D"
    public void deleteCart(String cartId) {
        cartRepository.deleteById(cartId);
    }

    public void deleteCartByUserId(Long userId) {
        cartRepository.findByUserId(userId).ifPresent(cart -> cartRepository.deleteById(cart.getCartId()));
    }

    //(1) get cart by userID
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    //(2) remove item from cart
    public boolean removeFromCart(Long userId, CartItem itemToRemove) {
        Optional<Cart> optionalCart = getCartByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            boolean removed = cart.getCartItemList().removeIf(item -> item.getMenuItemId().equals(itemToRemove.getMenuItemId()));
            if (removed) {
                cartRepository.save(cart);
                return true;
            }
        }
        return false;
    }

    public void addToCart(Long userId, CartItem cartItemToAdd) {
        Optional<Cart> optionalCart = getCartByUserId(userId);

        if (optionalCart.isEmpty()) throw new RuntimeException("No cart found for this user!");

        Cart cart = optionalCart.get();

        //check if cart already contains item
        Optional<CartItem> existingItem = cart.getCartItemList().stream().filter(item -> item.getMenuItemId().equals(cartItemToAdd.getMenuItemId())).findFirst();

        if (existingItem.isPresent()) {
            // if item exists, quantity+1
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItemToAdd.getQuantity());
        } else {
            // If new item, add it to the cart with the quantity
            cart.getCartItemList().add(cartItemToAdd);
        }

        // calculate total price
        double updatedTotalPrice = cart.getCartItemList().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        cart.setTotalPrice(updatedTotalPrice);

        cartRepository.save(cart);
    }

}
