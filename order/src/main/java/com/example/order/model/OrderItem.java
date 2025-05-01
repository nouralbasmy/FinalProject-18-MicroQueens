package com.example.order.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long menuItemId;
    private int quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonBackReference(value="order") // Prevents infinite recursion
    private Order order;

    public OrderItem() {
    }

    public OrderItem(long id, long menuItemId, int quantity, Order order) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.order = order;
    }

    public OrderItem(long menuItemId, int quantity, Order order) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
