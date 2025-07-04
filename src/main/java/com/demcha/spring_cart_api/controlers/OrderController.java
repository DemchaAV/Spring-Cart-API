package com.demcha.spring_cart_api.controlers;


import com.demcha.spring_cart_api.dtos.OrderDto;
import com.demcha.spring_cart_api.mappers.OrderMapper;
import com.demcha.spring_cart_api.repositories.OrderRepository;
import com.demcha.spring_cart_api.services.AuthService;
import com.demcha.spring_cart_api.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final AuthService authService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }
}
