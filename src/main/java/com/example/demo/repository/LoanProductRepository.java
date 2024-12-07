package com.example.demo.repository;

import com.example.demo.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface LoanProductRepository extends JpaRepository<LoanProduct, Integer> {
=======
public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {
>>>>>>> 9bb8b6ab31e14e7c2263b2e96959d6883bb6bb38
    LoanProduct getLoanProductsById(int id);
}
