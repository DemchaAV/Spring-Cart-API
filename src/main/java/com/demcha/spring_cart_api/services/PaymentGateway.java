package com.demcha.spring_cart_api.services;

import com.demcha.spring_cart_api.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}
