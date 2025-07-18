package com.demcha.spring_cart_api.carts;

import com.demcha.spring_cart_api.products.CartProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsDto {
    private CartProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
