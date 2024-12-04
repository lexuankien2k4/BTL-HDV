package com.example.demo.controller;

import com.example.demo.dto.LoanCalculationRequest;
import com.example.demo.dto.LoanCalculationResult;
import com.example.demo.entity.LoanProduct;
import com.example.demo.entity.Product;
import com.example.demo.service.LoanProductService;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loan-products")
public class LoanProductController {

    private final LoanProductService loanProductService;
    private final ProductService productService;

    public LoanProductController(LoanProductService loanProductService, ProductService productService) {
        this.loanProductService = loanProductService;
        this.productService = productService;
    }

    @PostMapping("/create")
    public LoanProduct createLoanProduct(@RequestParam int productId,
                                         @RequestParam double downPayment,
                                         @RequestParam double loanInterestRate,
                                         @RequestParam int loanTerm) {
        Product product = productService.getProductById(productId);

        LoanProduct loanProduct = new LoanProduct();
        loanProduct.setProduct(product);
        loanProduct.setDownPayment(downPayment);
        loanProduct.setLoanInterestRate(loanInterestRate);
        loanProduct.setLoanTerm(loanTerm);

        return loanProductService.createLoanProduct(loanProduct);
    }

    @GetMapping("/{id}")
    public LoanProduct getLoanProduct(@PathVariable int id) {
        return loanProductService.getLoanProductById(id);
    }

    @PostMapping("/calculate")
    @ResponseBody
    public ResponseEntity<?> calculateLoan(@RequestBody LoanCalculationRequest request) {
        try {
            // Fetch product details
            Product product = productService.getProductById(request.getProductId());

            // Calculate down payment and loan amount
            double downPayment = product.getPrice() * request.getDownPaymentPercentage() / 100;
            double loanAmount = product.getPrice() - downPayment;

            // Define interest rate (use fixed or product-based)
            double loanInterestRate = 8.5;
            double monthlyInterestRate = loanInterestRate / 12 / 100;

            // Calculate monthly installment using the formula
            double monthlyInstallment = (loanAmount * monthlyInterestRate) /
                    (1 - Math.pow(1 + monthlyInterestRate, -request.getLoanTerm()));

            // Prepare result
            LoanCalculationResult result = new LoanCalculationResult();
            result.setMonthlyInstallment(monthlyInstallment);
            result.setProduct(product);

            // Return result as JSON
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            // Return error if any exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot calculate loan: " + e.getMessage());
        }
    }



}
