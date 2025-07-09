package com.demcha.spring_cart_api.services;

import com.demcha.spring_cart_api.dtos.CheckoutRequest;
import com.demcha.spring_cart_api.dtos.CheckoutResponse;
import com.demcha.spring_cart_api.entities.Order;
import com.demcha.spring_cart_api.exeptions.CartEmptyException;
import com.demcha.spring_cart_api.exeptions.CartNotFoundException;
import com.demcha.spring_cart_api.exeptions.PaymentException;
import com.demcha.spring_cart_api.repositories.CartRepository;
import com.demcha.spring_cart_api.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;

    @Transactional
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

        // Create a checkout session

        try {
            CheckoutSession checkoutSession = paymentGateway.createCheckoutSession(order);


            cartService.clearCart(cart.getId());


            return new CheckoutResponse(order.getId(), checkoutSession.getCheckoutUrl());
        } catch (PaymentException e) {
            orderRepository.delete(order);
            throw e;
        }
    }
}
