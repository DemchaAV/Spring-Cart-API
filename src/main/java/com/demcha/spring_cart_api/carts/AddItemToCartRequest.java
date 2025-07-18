package com.demcha.spring_cart_api.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddItemToCartRequest implements Serializable {
    @NotNull
   private Long productId;
}