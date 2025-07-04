package com.demcha.spring_cart_api.controlers;

import com.demcha.spring_cart_api.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}