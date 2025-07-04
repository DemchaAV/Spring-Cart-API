package com.demcha.spring_cart_api.services;

import com.demcha.spring_cart_api.dtos.OrderDto;
import com.demcha.spring_cart_api.entities.Order;
import com.demcha.spring_cart_api.entities.User;
import com.demcha.spring_cart_api.mappers.OrderMapper;
import com.demcha.spring_cart_api.repositories.OrderRepository;
import com.demcha.spring_cart_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private AuthService authService;

    public List<OrderDto> getAllOrders() {
        User currentUser = authService.getCurrentUser();

        List<Order> orders = orderRepository.findAllByCustomer(currentUser);

        return orders.stream().map(orderMapper::toDto).toList();
    }
}
