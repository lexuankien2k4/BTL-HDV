package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Entity
@Table
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String carId;

    private int paymentBefore;

    private String timeContract;

    @Transient  // Trường không được lưu vào DB, chỉ dùng để hiển thị
    private String carType;

}
