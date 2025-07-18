package com.demcha.spring_cart_api.carts;

import com.demcha.spring_cart_api.common.ErrorDto;
import com.demcha.spring_cart_api.products.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/carts")
@Tag(name = "Carts", description = "Carts API")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriComponentsBuilder) {

        CartDto cartDto = cartService.createCart();
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId) {
        return cartService.getCartDto(cartId);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Add item to cart", description = "Add item to cart")
    public ResponseEntity<?> addToCart(
            UriComponentsBuilder uriComponentsBuilder,
            @Parameter(description = "Cart ID") @PathVariable UUID cartId, @Valid @RequestBody AddItemToCartRequest request) {

        CartItemsDto cartItemsDto = cartService.addCart(cartId, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemsDto);

    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemsDto updateItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {

        return cartService.updateItem(cartId, productId, request.getQuantity());

    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteItem(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId) {
        cartService.deleteItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> deleteAllItems(@PathVariable("cartId") UUID cartId) {

        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundInCart() {
        return ResponseEntity.badRequest().body(new ErrorDto("Product not found in the cart"));
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFound() {
        return ResponseEntity.badRequest().body(new ErrorDto("Cart not found"));
    }

}
