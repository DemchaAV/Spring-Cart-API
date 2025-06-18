package com.demcha.spring_cart_api.repositories;

import com.demcha.spring_cart_api.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @EntityGraph(attributePaths = "category")
    List<Product> findAllByCategory_Id(Byte categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("SELECT p FROM Product p ")
    List<Product> findAllWithCategory();

}