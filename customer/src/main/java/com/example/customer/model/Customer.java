package com.example.customer.model;
import jakarta.persistence.*;
import java.util.List;
import com.example.customer.model.Rating;


@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    //ADDED FOR REFUNDING (KNYHT)
    private double wallet = 0.0;

    @ElementCollection
    private List<Long> favouriteRestaurantIds;



    @OneToMany(mappedBy = "customer")
    private List<Rating> ratings;

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Long> getFavouriteRestaurants() {
        return favouriteRestaurantIds;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFavouriteRestaurants(List<Long> favouriteRestaurantIds) {
        this.favouriteRestaurantIds = favouriteRestaurantIds;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    // Constructors

    // 1. No-arg constructor
    public Customer() {
    }

    // 2. All-args constructor
    public Customer(Long id, String username, String email, String password,
                    List<Long> favouriteRestaurantIds, List<Rating> ratings) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.favouriteRestaurantIds = favouriteRestaurantIds;
        this.ratings = ratings;
    }

    // 3. Constructor without ID (for new objects before DB insert)
    public Customer(String username, String email, String password,
                    List<Long> favouriteRestaurantIds, List<Rating> ratings) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.favouriteRestaurantIds = favouriteRestaurantIds;
        this.ratings = ratings;
    }
}