package com.example.order.service;

import com.example.order.clients.MenuItemClient;
import com.example.order.model.Cart;
import com.example.order.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.order.model.CartItem;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final MenuItemClient menuItemClient;


    @Autowired
    public CartService(CartRepository cartRepository, MenuItemClient menuItemClient) {
        this.cartRepository = cartRepository;
        this.menuItemClient = menuItemClient;
    }

    // Basic CRUD operation
    // CREATE "C"
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    // GET cart by id "R"
    public Optional<Cart> getCartById(String cartId) {
        return cartRepository.findById(cartId);
    }

    public Iterable<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    // UPDATE "U"
    public Cart updateCart(Long userId, Cart updatedCartData) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isEmpty())
            throw new RuntimeException("No cart found for this user id");
        Cart existingCart = cart.get();
        // existingCart.setUserId(updatedCartData.getUserId());
        existingCart.setTotalPrice(updatedCartData.getTotalPrice());
        existingCart.setCartItemList(updatedCartData.getCartItemList());
        return cartRepository.save(existingCart);
    }

    // DELETE "D"
    public void deleteCart(String cartId) {
        cartRepository.deleteById(cartId);
    }

    public Cart applyDiscount(String cartId, double discountPercent) {

        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }

        Cart cart = optionalCart.get();
        double originalTotalPrice = cart.getCartItemList().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        double total = cart.getTotalPrice();
        //Added to ensure discount is applied only once -N
        if(originalTotalPrice != total)
        {
            throw new RuntimeException("Failed to apply! A discount has already been applied before");

        }
        double discountAmount = total * (discountPercent / 100.0);
        double newTotal = total - discountAmount;

        cart.setTotalPrice(newTotal);
        cartRepository.save(cart);

        return cart;
    }

    public void deleteCartByUserId(Long userId) {
        cartRepository.findByUserId(userId).ifPresent(cart -> cartRepository.deleteById(cart.getId()));
    }

    // (1) get cart by userID
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // (2) remove item from cart
    public boolean removeItemFromCart(Long userId, CartItem itemToRemove) {
        Optional<Cart> optionalCart = getCartByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            boolean removed = cart.getCartItemList()
                    .removeIf(item -> item.getMenuItemId().equals(itemToRemove.getMenuItemId()));
            if (removed) {
                double updatedTotalPrice = cart.getCartItemList().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
                cart.setTotalPrice(updatedTotalPrice);
                cartRepository.save(cart);
                if (cart.getCartItemList().isEmpty()) //if cart is empty after removing item, delete cart
                    deleteCart(cart.getId());
                return true;
            }
        }
        return false;
    }

    //(3)
    public void addToCart(Long userId, CartItem cartItemToAdd) {
        //inventory validationnn before allowing addToCart logic
        if(!menuItemClient.checkInventory(cartItemToAdd.getMenuItemId(), cartItemToAdd.getQuantity()))
        {
            throw new RuntimeException("Insufficient inventory for requested item!");
        }
        Optional<Cart> optionalCart = getCartByUserId(userId);
        if (optionalCart.isEmpty()) {
            //No cart found for this user -> create new cart and add item
            Cart cart = new Cart(userId, 0.0, new ArrayList<CartItem>());
            cart.getCartItemList().add(cartItemToAdd);
            double updatedTotalPrice = cart.getCartItemList().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
            cart.setTotalPrice(updatedTotalPrice);
            cartRepository.save(cart);
        } else {
            //cart found
            Cart cart = optionalCart.get();
            //check if user has empty cart or still adding items from same restaurant
            if (cart.getCartItemList().isEmpty() || cartItemToAdd.getRestaurantId().equals(cart.getCartItemList().getFirst().getRestaurantId())) {
                //same restaurant hence can still use same list
                Optional<CartItem> existingItem = cart.getCartItemList().stream()
                        .filter(item -> item.getMenuItemId().equals(cartItemToAdd.getMenuItemId())).findFirst();

                if (existingItem.isPresent()) {
                    // if item exists, add to quantity
                    CartItem item = existingItem.get();
                    //added inventory check henaaaa kman
                    if(!menuItemClient.checkInventory(item.getMenuItemId(),item.getQuantity() + cartItemToAdd.getQuantity()))
                    {
                        throw new RuntimeException("Insufficient inventory for requested item!");
                    }
                    item.setQuantity(item.getQuantity() + cartItemToAdd.getQuantity());
                } else {
                    //if new item, add it to cart
                    cart.getCartItemList().add(cartItemToAdd);
                }
                double updatedTotalPrice = cart.getCartItemList().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
                cart.setTotalPrice(updatedTotalPrice);
                cartRepository.save(cart);
            } else {
                //user trying to add item from a new restaurant hence will delete old cart, create new one, add item
                deleteCart(cart.getId());
                cart = new Cart(userId, 0.0, new ArrayList<CartItem>());
                cart.getCartItemList().add(cartItemToAdd);
                double updatedTotalPrice = cart.getCartItemList().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
                cart.setTotalPrice(updatedTotalPrice);
                cartRepository.save(cart);
            }
        }
    }

    // (6) explicitly checking inventory only -- for additional fn requirement
    public boolean checkInventory(Long menuItemId, int quantity)
    {
        return menuItemClient.checkInventory(menuItemId,quantity);
    }


}
