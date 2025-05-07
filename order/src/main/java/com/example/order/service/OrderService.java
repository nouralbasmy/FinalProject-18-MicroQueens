package com.example.order.service;

import com.example.order.clients.PaymentClient;
import com.example.order.clients.MenuItemClient;
import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.state.*;
import com.example.order.model.Cart;
import com.example.order.model.CartItem;
import com.example.order.model.OrderItem;
import com.example.order.rabbitmq.RabbitMQProducer;
import com.example.order.repository.OrderItemRepository;
import com.example.order.state.ConfirmedState;
import com.example.order.strategy.OrderFilterContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final OrderFilterContext orderFilterContext;


    private final PaymentClient paymentClient;
    private final MenuItemClient restaurantClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService, OrderItemRepository orderItemRepository, RabbitMQProducer rabbitMQProducer, OrderFilterContext orderFilterContext, PaymentClient paymentClient, MenuItemClient menuItemClient) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.orderItemRepository = orderItemRepository;
        this.rabbitMQProducer = rabbitMQProducer;
        this.orderFilterContext = orderFilterContext;
        this.paymentClient = paymentClient;
        this.restaurantClient = menuItemClient;
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        //Edited N
        Order orderWithState = order.get();
        orderWithState.setState(getState(orderWithState.getStatus()));
        return order.get();
    }

    public OrderState getState(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case REFUNDED -> new RefundedState();
            case CONFIRMED -> new ConfirmedState();
            case ON_THE_WAY -> new OnTheWayState();
            case DELIVERED -> new DeliveredState();
        };
    }

    public Order updateStatus(Long orderId) {
        Order order = getOrderById(orderId);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        //state design pattern
        order.next();

        return orderRepository.save(order);
    }

    public void deleteOrderById(Long orderId) {
        Order order = getOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

//    public List<Order> getOrdersByUserId(Long userId) {
//        return orderRepository.getOrdersByUserId(userId);
//    }
//
//    public List<Order> getOrdersByStatus(OrderStatus status) {
//        return orderRepository.getOrdersByStatus(status);
//    }
//
//    public List<Order> getOrdersByDate(LocalDateTime date) {
//        return orderRepository.getOrdersByOrderDate(date);
//    }

    public List<Order> filterOrders(String filterType, Object criteria) {
        return orderFilterContext.filter(filterType, orderRepository, criteria);
    }

    @Transactional
    public Order placeOrder(Long userId) {

        // Get cart from the user
        Optional<Cart> cart_optional = cartService.getCartByUserId(userId);

        if (cart_optional.isPresent()) {
            Cart cart = cart_optional.get();
//            if (cart == null) {
//                throw new RuntimeException("Cart not found ");
//            }

            Long restaurantId = cart.getCartItemList().get(0).getRestaurantId(); // Assuming same restaurant for all
            // items

            // Creating Order from Cart

            Order order = new Order(
                    userId,
                    LocalDateTime.now(),
                    OrderStatus.CONFIRMED,
                    cart.getTotalPrice(),
                    restaurantId,
                    new ConfirmedState());


//            Order order = new Order();
//            order.setUserId(userId);
//            order.setTotalPrice(cart.getTotalPrice());
////            order.setStatus("PENDING");
//            order.setStatus(OrderStatus.CONFIRMED);
//            order.setState(new ConfirmedState());
//            order.setOrderDate(LocalDateTime.now());
//            order.setRestaurantId(restaurantId);

            // Save the order
            Order orderPlaced = orderRepository.save(order);

            // Create OrderItems from CartItems
            for (CartItem cartItem : cart.getCartItemList()) {
                OrderItem orderItem = new OrderItem();

                // order
                orderItem.setOrder(order);

                // from cart
                orderItem.setMenuItemId(cartItem.getMenuItemId());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getPrice());


                orderItemRepository.save(orderItem);
            }

            // Delete Cart
            cartService.deleteCart(cart.getId());

            // Notify via RabbitMQ ("customerId;restaurantId")
            String message = userId + ";" + restaurantId;
            rabbitMQProducer.sendToNotification(message);

            return orderPlaced;
        } else {
            throw new RuntimeException("Cart not found");
        }
    }

    public Long checkout(Long userId, String paymentType, String extraInfo) {
        Optional<Cart> optionalCart = cartService.getCartByUserId(userId);
        if (optionalCart.isEmpty()) {
            throw new RuntimeException("No cart for this user to checkout");
        }
        Cart cart = optionalCart.get();
        Long paymentId = paymentClient.pay(paymentType, cart.getTotalPrice(), null, cart.getUserId(), extraInfo);
        Order order = placeOrder(userId);
        paymentClient.setPaymentOrderId(paymentId, order.getId());
        //decrement inventory call hereee
        for(CartItem cartItem : cart.getCartItemList())
        {
            restaurantClient.decrementInventory(cartItem.getMenuItemId(), cartItem.getQuantity());
        }
        return order.getId(); //order placed successfully!
    }
}