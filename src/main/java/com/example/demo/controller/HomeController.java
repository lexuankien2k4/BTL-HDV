package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("/contract")
    public String contract() {
        return "loanCalculator";
    }

    @GetMapping("/")
    public String home() {
        return "product";
    }
}
