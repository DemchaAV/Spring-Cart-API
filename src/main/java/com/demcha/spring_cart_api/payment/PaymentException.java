package com.demcha.spring_cart_api.payment;

import com.stripe.exception.StripeException;

public class PaymentException extends RuntimeException {
    public PaymentException(StripeException e) {
        super(e);
    }

    public PaymentException(String message) {
        super(message);
    }
}
