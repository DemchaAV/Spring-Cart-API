package com.demcha.spring_cart_api.repositories;

import com.demcha.spring_cart_api.entities.Order;
import com.demcha.spring_cart_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(User customer);
}