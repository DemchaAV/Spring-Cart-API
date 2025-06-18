package com.demcha.spring_cart_api.repositories;

import com.demcha.spring_cart_api.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}