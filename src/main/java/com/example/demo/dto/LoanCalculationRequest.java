package com.example.demo.dto;

public class LoanCalculationRequest {
    private int productId;
    private int downPaymentPercentage;
    private int loanTerm;

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDownPaymentPercentage() {
        return downPaymentPercentage;
    }

    public void setDownPaymentPercentage(int downPaymentPercentage) {
        this.downPaymentPercentage = downPaymentPercentage;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }
}

