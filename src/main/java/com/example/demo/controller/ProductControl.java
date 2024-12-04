package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductControl {
    private final ProductService productService;

    @GetMapping("/api/products/{productId}")
    public Product getProduct(@PathVariable int productId) {
        return productService.getProductById(productId);
    }
}
