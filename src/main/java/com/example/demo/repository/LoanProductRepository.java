package com.example.demo.repository;

import com.example.demo.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProductRepository extends JpaRepository<LoanProduct, Integer> {
    LoanProduct getLoanProductsById(int id);
}
