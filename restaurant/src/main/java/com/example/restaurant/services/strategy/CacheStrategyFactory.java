package com.example.restaurant.services.strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CacheStrategyFactory {
    @Autowired private InMemoryCacheStrategy inMemory;
    @Autowired private RedisCacheStrategy redis;

    public CacheStrategy getStrategy(String type) {
        return switch (type.toLowerCase()) {
            case "memory" -> inMemory;
            case "redis" -> redis;
            default -> throw new IllegalArgumentException("Unknown strategy type");
        };
    }
}