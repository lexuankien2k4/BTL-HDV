package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.entity.Product;

public interface ProductService {
	Product createProduct(Product product);

	Product saveProduct(Product product);
	Product updateProduct(Product product);

	Product getProductById(Integer productId);

	void deleteProduct(Integer productId);

	List<Product> getAll();
	Page<Product> getAll(Integer pageNo);
}
