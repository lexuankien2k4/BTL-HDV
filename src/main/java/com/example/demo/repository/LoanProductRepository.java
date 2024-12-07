package com.example.demo.repository;

import com.example.demo.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanProductRepository extends JpaRepository<LoanProduct, Integer> {

    LoanProduct getLoanProductsById(int id);
}
