package com.example.restaurant.services.strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Optional;
import com.example.restaurant.model.Restaurant;

@Component
public class RedisCacheStrategy implements CacheStrategy {
    @Autowired
    private RedisTemplate<String, Restaurant> redisTemplate;

    public Optional<Restaurant> getRestaurantById(Long id) {
        return Optional.ofNullable(redisTemplate.opsForValue().get("restaurant::" + id));
    }

    public void saveRestaurant(Restaurant restaurant) {
        redisTemplate.opsForValue().set("restaurant::" + restaurant.getId(), restaurant);
    }
}
