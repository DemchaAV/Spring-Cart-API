package com.demcha.spring_cart_api.orders;

import com.demcha.spring_cart_api.users.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.customer = :customer")
    @EntityGraph(attributePaths = "items.product")
    List<Order> findOrdersByCustomer(@Param("customer") User customer);

    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    @EntityGraph(attributePaths = "items.product")
    Optional<Order> getOrderWithItems(@Param("orderId") Long orderId);
}