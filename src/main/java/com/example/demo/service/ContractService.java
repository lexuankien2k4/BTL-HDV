package com.example.demo.service;

import com.example.demo.entity.Contract;
import com.example.demo.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    public List<Contract> getContracts() {
        return contractRepository.findAll();
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }
}
