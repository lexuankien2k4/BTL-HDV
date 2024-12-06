package com.example.demo.service;

import com.example.demo.entity.Contract;
import com.example.demo.entity.Product;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final ProductsRepository productsRepository;

    public List<Contract> getContracts() {
        return contractRepository.findAll();
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public long countContracts() {
        return contractRepository.count();
    }

    // Tính tổng số tiền thanh toán trước dưới dạng phần trăm
    public BigDecimal getTotalPaymentBefore() {
        return contractRepository.findAll().stream()
                .map(contract -> new BigDecimal(contract.getPaymentBefore()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Lấy loại xe từ bảng Product theo carId (productId)
    public String getCarTypeByProductId(int productId) {
        Optional<Product> product = productsRepository.findById(productId);
        Product product1 = product.get();
        if (product1 != null) {
            return product1.getCategory();  // Trả về loại xe (category)
        }
        return "Unknown";  // Nếu không tìm thấy sản phẩm
    }
}
