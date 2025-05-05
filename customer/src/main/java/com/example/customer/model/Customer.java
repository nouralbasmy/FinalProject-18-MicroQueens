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

// Constructors

    // 1. No-arg constructor
    public Customer() {
    }
    // Private constructor to enforce builder usage
    private Customer(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.favouriteRestaurantIds = builder.favouriteRestaurantIds;
        this.ratings = builder.ratings;
    }

//    // 2. All-args constructor
//    public Customer(Long id, String username, String email, String password,
//                    List<Long> favouriteRestaurantIds, List<Rating> ratings) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.favouriteRestaurantIds = favouriteRestaurantIds;
//        this.ratings = ratings;
//    }
//

    // 3. Constructor without ID (for new objects before DB insert)
    public Customer(String username, String email, String password,
                    List<Long> favouriteRestaurantIds, List<Rating> ratings) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.favouriteRestaurantIds = favouriteRestaurantIds;
        this.ratings = ratings;
    }
    // 🧱 Static nested Builder class
    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private List<Long> favouriteRestaurantIds;
        private List<Rating> ratings;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder favouriteRestaurantIds(List<Long> favouriteRestaurantIds) {
            this.favouriteRestaurantIds = favouriteRestaurantIds;
            return this;
        }

        public Builder ratings(List<Rating> ratings) {
            this.ratings = ratings;
            return this;
        }

        public Customer build() {
            return new Customer(this);  // 👈 uses the private constructor
        }
    }

}