package com.example.demo.dto;

import lombok.Data;

@Data
public class UserViewDTO {
    private Long id;
    private String name;
    private String email;
    // Password bilgisi response'da dönülmeyecek
}