package com.example.demo;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductsRepository;


@SpringBootApplication
public class DemoApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApiApplication.class, args);
		
	}
	 @Autowired
	 private ProductsRepository productsrepository;
	 
	 
	

	@Override
	public void run(String... args) throws Exception {
//		Product product1 = new Product();
//		product1.setName("VinFast VF9");
//		product1.setBrand("VinFast");
//		product1.setCategory("Sedan");
//		product1.setPrice(990000000.0);
//		product1.setCreated_at(Date.valueOf("2024-01-15"));
//		product1.setImageFileName("NAU.png");
//		productsrepository.save(product1);
		 
		
	}

}
