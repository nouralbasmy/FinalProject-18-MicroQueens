package com.example.order.repository;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value="SELECT o FROM Order o WHERE o.userId = :userId")
    List<Order> getOrdersByUserId(@Param("userId") Long userId);

    @Query(value="SELECT o FROM Order o WHERE o.status = :status")
    List<Order> getOrdersByStatus(@Param("status") OrderStatus status);

    @Query(value="SELECT o FROM Order o WHERE o.orderDate = :orderDate")
    List<Order> getOrdersByOrderDate(@Param("orderDate") LocalDateTime orderDate);
}
