package com.example.demo.controller;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductDto;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ProductsController {

	ProductService productservice;

	public ProductsController(ProductService productservice) {
		this.productservice = productservice;
	}


	@GetMapping("/products")
	public String index(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		List<Product> product = this.productservice.getAll();

		model.addAttribute("product", product);
		return "productlist";
	}

	@GetMapping("/products/new")
	public String createProductForm(Model model) {
		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto", productDto);
		return "createProduct";
	}

	@PostMapping("/products/new")
	public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
		if (productDto.getImageFile().isEmpty()) {
			result.addError(new FieldError("productDto", "imageFile", "The image file is required"));
		}

		if (result.hasErrors()) {
			return "products/new";
		}

		// save image file
		MultipartFile image = productDto.getImageFile();
		Date created_at = new Date();
		String storageFileName = created_at.getTime() + "_" + image.getOriginalFilename();
		try {
			String uploadDir = "public/images/";
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception ex) {
				System.out.println("Exception: " + ex.getMessage());

			}
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());

		}
		Product product = new Product();
		product.setName(productDto.getName());
		product.setBrand(productDto.getBrand());
		product.setCategory(productDto.getCategory());
		product.setPrice(productDto.getPrice());
		product.setCreated_at(created_at);
		product.setImageFileName(storageFileName);

		productservice.saveProduct(product);

		return "redirect:/products";
	}

	@PostMapping("/products")
	public String saveProduct(@ModelAttribute Product product) {
		productservice.saveProduct(product);

		return "redirect:/products";
	}

	@GetMapping("/products/{id}/loan")
	public String viewLoanCalculator(@PathVariable int id, Model model) {
		try {
			Product product = productservice.getProductById(id);
			model.addAttribute("product", product);
		} catch (Exception e) {
			model.addAttribute("error", "Product not found: " + e.getMessage());
			return "error";
		}

		return "loanCalculator";
	}


	// edit
	@RequestMapping("/products/edit/{id}")
	public String showEditProduct(Model model, @PathVariable int id) {
		try {
			// Find the product by ID
			Product product = productservice.getProductById(id);
			model.addAttribute("product", product);

			// Create a ProductDto to populate the form
			ProductDto productDto = new ProductDto();
			productDto.setName(product.getName());
			productDto.setBrand(product.getBrand());
			productDto.setCategory(product.getCategory());
			productDto.setPrice(product.getPrice());

			// Add both ProductDto to the model

			model.addAttribute("productDto", productDto);

		} catch (Exception ex) {
			System.out.println("Exception:" + ex.getMessage());
			return "redirect:/products";
		}
		return "editProduct";
	}

	// edit
	@PostMapping("/products/edit/{id}")
	public String updateProduct(Model model,
								@PathVariable int id,
								@Valid @ModelAttribute ProductDto productDto,
								BindingResult result) {

		try {
			Product product = productservice.getProductById(id);
			model.addAttribute("product", product);
			if (result.hasErrors()) {
				return "products/editProduct";
			}
			if (!productDto.getImageFile().isEmpty()) {
				// delete old image
				String uploadDir = "public/images/";
				Path oldImagePath = Paths.get(uploadDir, product.getImageFileName());

				try {
					Files.delete(oldImagePath); // Delete the old image only if it exists
				} catch (IOException ex) {
					System.out.println("exception: " + ex.getMessage());
				}

				// save new image file
				MultipartFile image = productDto.getImageFile();

				String storageFileName = image.getOriginalFilename();

				try (InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				}
				product.setImageFileName(storageFileName);
			}
			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setCategory(productDto.getCategory());
			product.setPrice(productDto.getPrice());

			productservice.saveProduct(product);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex.getMessage());
		}

		return "redirect:/products";
	}

	@GetMapping("/products/{Id}")
	public String deleteProduct(@PathVariable Integer Id) {
		productservice.deleteProduct(Id);
		return "redirect:/products";
	}


}
