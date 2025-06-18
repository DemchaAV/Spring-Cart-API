package com.demcha.spring_cart_api.controlers;


import com.demcha.spring_cart_api.dtos.ProductDto;
import com.demcha.spring_cart_api.entities.Category;
import com.demcha.spring_cart_api.entities.Product;
import com.demcha.spring_cart_api.mappers.ProductMapper;
import com.demcha.spring_cart_api.repositories.CategoryRepository;
import com.demcha.spring_cart_api.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll(
            @RequestParam(name = "category_id", required = false) Byte categoryId
    ) {

        if (categoryId == null) {
            return ResponseEntity.ok(productRepository.findAllWithCategory()
                    .stream()
                    .map(productMapper::toDto)
                    .toList());
        } else {
            var prods = productRepository.findAllByCategory_Id(categoryId)
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
            if (prods == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(prods);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            return ResponseEntity.ok(productMapper.toDto(product));
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, UriComponentsBuilder builder) {
        Product entity = productMapper.toEntity(productDto);
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        entity.setCategory(category);
        productRepository.save(entity);
        if (entity.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        var uri = builder.path("/products/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(productMapper.toDto(entity));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        Product entity = productRepository.findById(id).orElse(null);
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);

        if (entity != null) {
            if (category != null) {
                entity.setCategory(category);
            }
            productMapper.updateEntity(productDto, entity);
            Product product = productRepository.save(entity);

            if (product.getId() != null) {
                return ResponseEntity.ok(productMapper.toDto(product));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") Long id) {
        Product entity = productRepository.findById(id).orElse(null);
        if (entity != null) {
            productRepository.delete(entity);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
