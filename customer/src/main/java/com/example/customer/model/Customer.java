package com.example.customer.model;
package com.example.restaurant.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "customer_favourites",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> favouriteRestaurants;

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

    public List<Restaurant> getFavouriteRestaurants() {
        return favouriteRestaurants;
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

    public void setFavouriteRestaurants(List<Restaurant> favouriteRestaurants) {
        this.favouriteRestaurants = favouriteRestaurants;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

// Constructors

    // 1. No-arg constructor
    public Customer() {
    }

    // 2. All-args constructor
    public Customer(Long id, String username, String email, String password,
                    List<Restaurant> favouriteRestaurants, List<Rating> ratings) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.favouriteRestaurants = favouriteRestaurants;
        this.ratings = ratings;
    }

    // 3. Constructor without ID (for new objects before DB insert)
    public Customer(String username, String email, String password,
                    List<Restaurant> favouriteRestaurants, List<Rating> ratings) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.favouriteRestaurants = favouriteRestaurants;
        this.ratings = ratings;
    }
}