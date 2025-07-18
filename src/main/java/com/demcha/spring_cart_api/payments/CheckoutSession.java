package com.demcha.spring_cart_api.payments;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckoutSession {
    private  String checkoutUrl;
}
