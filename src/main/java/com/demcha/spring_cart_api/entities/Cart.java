package com.demcha.spring_cart_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        totalPrice = items.stream().map(CartItem::calculateTotalPrice).reduce(totalPrice, BigDecimal::add);
        return totalPrice;

    }

    public Optional<CartItem> getItem(Long productId) {
        return items.stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();
    }

    public CartItem addItems(Product product) {
        var cartItem = getItem(product.getId()).orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);

        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(this);
            items.add(cartItem);
        }
        return cartItem;
    }

    public void removeItem(Long productId) {
        var item = getItem(productId).orElse(null);
        if (item == null) {
          return;
        }
        items.remove(item);
        item.setCart(null);
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }


}