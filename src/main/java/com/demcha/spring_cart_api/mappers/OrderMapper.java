package com.demcha.spring_cart_api.mappers;


import com.demcha.spring_cart_api.dtos.OrderDto;
import com.demcha.spring_cart_api.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
