package com.demcha.spring_cart_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
public class AddItemToCartRequest implements Serializable {
    @NotNull
   private Long productId;
}