package com.example.demo.controller;

import com.example.demo.entity.Contract;
import com.example.demo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        // Lấy danh sách hợp đồng
        List<Contract> contracts = contractService.getContracts();

        // Lấy danh sách loại xe tương ứng với carId (productId)
        for (Contract contract : contracts) {
            String carType = contractService.getCarTypeByProductId(Integer.parseInt(contract.getCarId()));
            contract.setCarType(carType);  // Set lại loại xe cho hợp đồng
        }

        // Truyền các dữ liệu vào model cho Thymeleaf
        model.addAttribute("totalContracts", contractService.countContracts());
        model.addAttribute("totalPaymentBefore", contractService.getTotalPaymentBefore());
        model.addAttribute("contracts", contracts);

        return "dashboard";
    }
}
