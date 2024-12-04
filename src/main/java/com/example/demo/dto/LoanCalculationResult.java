package com.example.demo.dto;

import com.example.demo.entity.Product;

public class LoanCalculationResult {
    private double monthlyInstallment;
    private Product product;

    public double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public void setMonthlyInstallment(double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

