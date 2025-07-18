package com.demcha.spring_cart_api.carts;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart Not Found.");
    }
}
