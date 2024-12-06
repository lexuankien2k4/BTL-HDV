package com.example.demo.service;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Product;
import com.example.demo.repository.ProductsRepository;
@Service
public class ProductServiceImpl implements ProductService {
	
	ProductsRepository productsrepository;
	
	
	public ProductServiceImpl(ProductsRepository productsrepository) {
		this.productsrepository = productsrepository;
	}


	@Override
	public Product createProduct(Product product) {
		return productsrepository.save(product);
	}


	@Override
	public Product saveProduct(Product product) {
		
		return productsrepository.save(product);
	}


	@Override
	public Product getProductById(int productId) {
		return productsrepository.getProductById(productId);
	}


	@Override
	public void deleteProduct(Integer Id) {
		productsrepository.deleteById(Id);
		
	}


	@Override
	public List<Product> getAll() {
		return productsrepository.findAll();
	}


	@Override
	public Product updateProduct(Product product) {
		return productsrepository.save(product);
	}


	@Override
	public Page<Product> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, 5);
		
		return this.productsrepository.findAll(pageable);
	}

	



}
