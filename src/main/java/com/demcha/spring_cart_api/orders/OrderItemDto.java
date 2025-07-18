package com.demcha.spring_cart_api.orders;

import com.demcha.spring_cart_api.products.OrderProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private OrderProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;

}
