package com.example.order.service;

import com.example.order.model.Cart;
import com.example.order.model.CartItem;
import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import com.example.order.rabbitmq.RabbitMQProducer;
import com.example.order.repository.OrderItemRepository;
import com.example.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;


    @Transactional
    public void placeOrder(Long userId) {

        //Get cart from the user
        Optional<Cart> cart_optional = cartService.getCartByUserId(userId);

        if (cart_optional.isPresent()) {
            Cart cart = cart_optional.get();
            if (cart == null) {
                throw new RuntimeException("Cart not found ");
            }

            Long restaurantId = cart.getCartItemList().get(0).getRestaurantId(); // Assuming same restaurant for all items

            //Creating Order from Cart
            Order order = new Order();
            order.setUserId(userId);
            order.setTotalPrice(cart.getTotalPrice());
            order.setStatus("PENDING");
            order.setOrderDate(LocalDateTime.now());
            order.setRestaurantId(restaurantId);

            // Save the order
            orderRepository.save(order);

            //Create OrderItems from CartItems
            for (CartItem cartItem : cart.getCartItemList()) {
                OrderItem orderItem = new OrderItem();

                //order
                orderItem.setOrder(order);

                //from cart
                orderItem.setMenuItemId(cartItem.getMenuItemId());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getPrice());

                orderItemRepository.save(orderItem);
            }

            //Delete Cart
            cartService.deleteCart(cart.getCartId());

            //Notify via RabbitMQ ("customerId;restaurantId")
            String message = userId + ";" + restaurantId;
            rabbitMQProducer.sendToNotification(message);
        }
        else{
            throw new RuntimeException("Cart not found");
        }
    }
}
