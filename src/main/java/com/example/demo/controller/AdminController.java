package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.repository.TaiKhoanRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private TaiKhoanRepository repository;

    @GetMapping("")
    public String adminPage(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username != null && password != null) {
            return "admin/index";
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return "redirect:/admin";
        }
        return "admin/logon";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        Optional<TaiKhoan> taiKhoan = repository.findByUsernameAndPassword(username, password);
        if (taiKhoan.isPresent()) {
            session.setAttribute("username", username);
            session.setAttribute("password", password);

            return "redirect:/admin";
        }
        redirectAttributes.addFlashAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
        return "redirect:/admin/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
