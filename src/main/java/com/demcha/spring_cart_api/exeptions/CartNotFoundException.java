package com.demcha.spring_cart_api.exeptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart Not Found.");
    }
}
