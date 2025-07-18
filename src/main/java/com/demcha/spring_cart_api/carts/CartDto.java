package com.demcha.spring_cart_api.carts;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private List<CartItemsDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;



}
