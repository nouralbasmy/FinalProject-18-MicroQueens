package com.example.customer.clients;
import com.example.customer.dto.RatingSummaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Restaurant-Rating" , url = "${restaurant.url}")
public interface CustomerRatingClient {

    @PostMapping("/restaurants/{restaurantId}/rating-summary")
    void sendRating(@PathVariable("restaurantId") Long restaurantId,
                    @RequestBody RatingSummaryDTO summary);
}
