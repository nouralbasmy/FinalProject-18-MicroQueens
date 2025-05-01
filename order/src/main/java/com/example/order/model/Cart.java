package com.example.order.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private Long id;
    private Long userId;
    private double totalPrice;
    private List<Double> priceList = new ArrayList<Double>();
    private List<Long> menuItemIdsList = new ArrayList<Long>();
    private List<Integer> quantityList = new ArrayList<Integer>();

    public Cart() {
    }

    public Cart(Long id, Long userId, double totalPrice, List<Double> priceList, List<Long> menuItemIdsList, List<Integer> quantityList) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.priceList = priceList;
        this.menuItemIdsList = menuItemIdsList;
        this.quantityList = quantityList;
    }

    public Cart(Long userId, double totalPrice, List<Double> priceList, List<Long> menuItemIdsList, List<Integer> quantityList) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.priceList = priceList;
        this.menuItemIdsList = menuItemIdsList;
        this.quantityList = quantityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Double> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Double> priceList) {
        this.priceList = priceList;
    }

    public List<Long> getMenuItemIdsList() {
        return menuItemIdsList;
    }

    public void setMenuItemIdsList(List<Long> menuItemIdsList) {
        this.menuItemIdsList = menuItemIdsList;
    }

    public List<Integer> getQuantityList() {
        return quantityList;
    }

    public void setQuantityList(List<Integer> quantityList) {
        this.quantityList = quantityList;
    }
}
