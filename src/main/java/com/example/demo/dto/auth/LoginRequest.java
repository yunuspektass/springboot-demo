package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Kullanıcı adı boş bırakılamaz")
    private String username;

    @NotBlank(message = "Şifre boş bırakılamaz")
    private String password;
}