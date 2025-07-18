package com.demcha.spring_cart_api.carts;

import com.demcha.spring_cart_api.products.CartProductDto;
import com.demcha.spring_cart_api.products.Product;
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
