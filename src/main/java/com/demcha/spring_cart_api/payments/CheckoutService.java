package com.demcha.spring_cart_api.payments;

import com.demcha.spring_cart_api.orders.Order;
import com.demcha.spring_cart_api.carts.CartEmptyException;
import com.demcha.spring_cart_api.carts.CartNotFoundException;
import com.demcha.spring_cart_api.carts.CartRepository;
import com.demcha.spring_cart_api.orders.OrderRepository;
import com.demcha.spring_cart_api.auth.AuthService;
import com.demcha.spring_cart_api.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void handleWebhookEven(WebhookRequest request) {
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                            Order order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                            order.setStatus(paymentResult.getPaymentStatus());
                            orderRepository.save(order);
                        }
                );


    }
}
