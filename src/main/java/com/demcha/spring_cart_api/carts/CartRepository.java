package com.demcha.spring_cart_api.carts;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT c FROM Cart c  WHERE c.id = :cardId")
    Optional<Cart> getCartWithItems(@Param("cardId") UUID cartId);
}