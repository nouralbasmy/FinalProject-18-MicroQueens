package com.example.order.model;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RedisHash("Cart")
public class Cart implements Serializable {
    @Id
    private String id;
    @Indexed
    private Long userId;
    private double totalPrice = 0.0;
    private List<CartItem> cartItemList = new ArrayList<CartItem>();

    @TimeToLive
    private Long ttl = 1800L; // ttl = 30 min

    public Cart() {
    }

    public Cart(String id, Long userId, double totalPrice, List<CartItem> cartItemList) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.cartItemList = cartItemList;
    }

    public Cart(Long userId, double totalPrice, List<CartItem> cartItemList) {
        this.id = UUID.randomUUID().toString(); // to auto-generate cart ids in cache
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.cartItemList = cartItemList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
