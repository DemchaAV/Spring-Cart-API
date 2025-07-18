package com.demcha.spring_cart_api.carts;

import com.demcha.spring_cart_api.products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    public BigDecimal calculateTotalPrice() {
        if (this.getProduct() != null && this.getProduct().getPrice() != null && this.getQuantity() != null) {
            return this.getProduct().getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
        }
        return BigDecimal.ZERO;
    }

}