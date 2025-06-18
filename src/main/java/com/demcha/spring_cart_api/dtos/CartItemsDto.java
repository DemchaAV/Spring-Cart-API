package com.demcha.spring_cart_api.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsDto {
    private CartProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
