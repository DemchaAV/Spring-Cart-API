package com.demcha.spring_cart_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;
}
