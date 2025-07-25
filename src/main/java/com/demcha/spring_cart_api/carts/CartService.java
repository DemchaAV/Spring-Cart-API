package com.demcha.spring_cart_api.carts;

import com.demcha.spring_cart_api.products.Product;
import com.demcha.spring_cart_api.products.ProductNotFoundException;
import com.demcha.spring_cart_api.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;


    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemsDto addCart(UUID cartId, Long productId) {
        var cart = getCart(cartId);

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }


        CartItem cartItem = cart.addItems(product);

        cartRepository.save(cart);

        return cartMapper.itemsToDto(cartItem);

    }

    public CartDto getCartDto(UUID cartId) {
        Cart cart = getCart(cartId);
        return cartMapper.toDto(cart);
    }

    public CartItemsDto updateItem(UUID cartId, Long productId, Integer quantity) {
        Cart cart = getCart(cartId);
        CartItem cartItem = cart.getItem(productId).orElse(null);
        if (cartItem == null) {
            throw new ProductNotFoundException();
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.itemsToDto(cartItem);
    }

    private Cart getCart(UUID cartId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        return cart;
    }

    public void deleteItem(UUID cartId, Long productId) {
        var cart = getCart(cartId);

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = getCart(cartId);

        cart.clear();
        cartRepository.save(cart);
    }
}
