package com.demcha.spring_cart_api.repositories;

import com.demcha.spring_cart_api.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}