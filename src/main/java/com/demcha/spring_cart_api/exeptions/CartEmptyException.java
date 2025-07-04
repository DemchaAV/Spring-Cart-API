package com.demcha.spring_cart_api.exeptions;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {
        super("Cart it Empty.");
    }

}
