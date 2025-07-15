package com.demcha.spring_cart_api.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequest {
    @NotNull(message = "Carat ID is required.")
    private UUID cartId;

}
