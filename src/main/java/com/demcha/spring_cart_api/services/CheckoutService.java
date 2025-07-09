package com.demcha.spring_cart_api.services;

import com.demcha.spring_cart_api.dtos.CheckoutRequest;
import com.demcha.spring_cart_api.dtos.CheckoutResponse;
import com.demcha.spring_cart_api.entities.Order;
import com.demcha.spring_cart_api.exeptions.CartEmptyException;
import com.demcha.spring_cart_api.exeptions.CartNotFoundException;
import com.demcha.spring_cart_api.repositories.CartRepository;
import com.demcha.spring_cart_api.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) throws StripeException {
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
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel");

            order.getItems().forEach(orderItem -> {
                var lineItem =
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(Long.valueOf(orderItem.getQuantity()))
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmountDecimal(orderItem.getUnitPrice()
                                                        .multiply(
                                                                BigDecimal.valueOf(100)
                                                        )
                                                )
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(orderItem.getProduct().getName())
                                                                .build()
                                                )
                                                .build()

                                )
                                .build();
                builder.addLineItem(lineItem);

            });
            Session session = Session.create(builder.build());


            cartService.clearCart(cart.getId());


            return new CheckoutResponse(order.getId(), session.getUrl());
        } catch (StripeException e) {
            e.printStackTrace();
            orderRepository.delete(order);
            throw e;
        }
    }
}
