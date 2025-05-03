package com.example.order.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {

    private String cartId;
    private Long userId;
    private double totalPrice;
    private List<CartItem> cartItemList = new ArrayList<CartItem>();

    public Cart() {
    }

    public Cart(String cartId, Long userId, double totalPrice, List<CartItem> cartItemList) {
        this.cartId = cartId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.cartItemList = cartItemList;
    }

    public Cart(Long userId, double totalPrice, List<CartItem> cartItemList) {
        this.cartId = UUID.randomUUID().toString(); //to auto-generate cart ids in cache
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.cartItemList = cartItemList;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
