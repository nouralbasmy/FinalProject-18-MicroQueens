package com.example.customer.clients;

import com.example.customer.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Restaurant-Info", url = "${restaurant.url}")
public interface RestaurantClient {

    @PostMapping("/restaurants/getRestaurantInfo")
    List<RestaurantDTO> getRestaurantsByIds(@RequestBody List<Long> ids);
}
