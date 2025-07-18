package com.demcha.spring_cart_api.carts;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {
        super("Cart it Empty.");
    }

}
