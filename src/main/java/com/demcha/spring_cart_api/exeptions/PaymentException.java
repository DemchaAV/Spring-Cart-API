package com.demcha.spring_cart_api.exeptions;

import com.stripe.exception.StripeException;

public class PaymentException extends RuntimeException {
    public PaymentException(StripeException e) {
        super(e);
    }
}
