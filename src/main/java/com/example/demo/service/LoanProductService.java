package com.example.demo.service;

import com.example.demo.entity.LoanProduct;
import com.example.demo.repository.LoanProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoanProductService {

    private final LoanProductRepository loanProductRepository;

    public LoanProductService(LoanProductRepository loanProductRepository) {
        this.loanProductRepository = loanProductRepository;
    }

    public LoanProduct createLoanProduct(LoanProduct loanProduct) {
        // Tính toán số tiền trả góp hàng tháng
        double loanAmount = loanProduct.getProduct().getPrice() - loanProduct.getDownPayment();
        double monthlyInterestRate = loanProduct.getLoanInterestRate() / 12 / 100;
        double monthlyInstallment = (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -loanProduct.getLoanTerm()));

        loanProduct.setMonthlyInstallment(monthlyInstallment);
        loanProduct.setCreatedAt(new Date());

        // Lưu vào database
        return loanProductRepository.save(loanProduct);
    }

    public LoanProduct getLoanProductById(int id) {
        return loanProductRepository.getLoanProductsById(id);
    }
}
