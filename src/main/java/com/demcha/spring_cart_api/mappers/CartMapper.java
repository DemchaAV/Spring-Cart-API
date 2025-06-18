package com.demcha.spring_cart_api.mappers;

import com.demcha.spring_cart_api.dtos.CartDto;
import com.demcha.spring_cart_api.dtos.CartItemsDto;
import com.demcha.spring_cart_api.dtos.CartProductDto;
import com.demcha.spring_cart_api.entities.Cart;
import com.demcha.spring_cart_api.entities.CartItem;
import com.demcha.spring_cart_api.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(source = "product", target = "product")
    @Mapping( target = "totalPrice", expression = "java(cartItem.calculateTotalPrice())")
    CartItemsDto itemsToDto(CartItem cartItem);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    CartProductDto toCartProductDto(Product product);


}
