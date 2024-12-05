package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductControl {
    private final ProductService productService;

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable int productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("")
    public List<Product> getProducts() {
        return productService.getAll();
    }
}
