package com.example.demo.controller;

import com.example.demo.dto.LoanCalculationRequest;
import com.example.demo.dto.LoanCalculationResult;
import com.example.demo.entity.Contract;
import com.example.demo.entity.LoanProduct;
import com.example.demo.entity.Product;
import com.example.demo.service.ContractService;
import com.example.demo.service.LoanProductService;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loan-products")
@RequiredArgsConstructor
public class LoanProductController {

    private final LoanProductService loanProductService;
    private final ProductService productService;
    private final ContractService contractService;


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

    @PostMapping("/calculate-installments")
    @ResponseBody
    public ResponseEntity<?> calculateInstallments(@RequestBody LoanCalculationRequest request) {
        try {
            // Lấy thông tin sản phẩm
            Product product = productService.getProductById(request.getProductId());

            // Tính toán khoản trả trước và khoản vay
            double downPayment = product.getPrice() * request.getDownPaymentPercentage() / 100;
            double loanAmount = product.getPrice() - downPayment;

            // Định nghĩa lãi suất và các thông số
            double loanInterestRate = 8.5; // Lãi suất hàng năm
            double monthlyInterestRate = loanInterestRate / 12 / 100;
            int loanTerm = request.getLoanTerm();

            // Tạo danh sách kết quả chi tiết từng kỳ thanh toán
            List<Map<String, Object>> installmentDetails = new ArrayList<>();

            double remainingPrincipal = loanAmount;

            for (int i = 1; i <= loanTerm; i++) {
                double interestPayment = remainingPrincipal * monthlyInterestRate;
                double principalPayment = loanAmount / loanTerm;
                double totalPayment = interestPayment + principalPayment;

                // Thêm thông tin vào danh sách kết quả
                Map<String, Object> installment = new HashMap<>();
                installment.put("month", i);
                installment.put("remainingPrincipal", remainingPrincipal);
                installment.put("principalPayment", principalPayment);
                installment.put("interestPayment", interestPayment);
                installment.put("totalPayment", totalPayment);

                installmentDetails.add(installment);

                // Cập nhật số dư gốc còn lại
                remainingPrincipal -= principalPayment;
            }
            Contract contract = new Contract();
            contract.setCarId(String.valueOf(request.getProductId()));
            contract.setTimeContract(String.valueOf(request.getLoanTerm()));
            contract.setPaymentBefore(request.getDownPaymentPercentage());
            contractService.createContract(contract);
            // Trả về danh sách kết quả dưới dạng JSON
            return ResponseEntity.ok(installmentDetails);

        } catch (Exception e) {
            // Trả về lỗi nếu có bất kỳ ngoại lệ nào xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot calculate installments: " + e.getMessage());
        }
    }



}
