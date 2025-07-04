package com.demcha.spring_cart_api.services;

import com.demcha.spring_cart_api.dtos.CheckoutRequest;
import com.demcha.spring_cart_api.dtos.CheckoutResponse;
import com.demcha.spring_cart_api.entities.Order;
import com.demcha.spring_cart_api.exeptions.CartEmptyException;
import com.demcha.spring_cart_api.exeptions.CartNotFoundException;
import com.demcha.spring_cart_api.repositories.CartRepository;
import com.demcha.spring_cart_api.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;

    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);

        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }
        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        cartService.clearCart(cart.getId());


        return new CheckoutResponse(order.getId());
    }
}
