package com.example.demo.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressCreateDTO {
    
    @NotBlank(message = "Sokak adresi boş bırakılamaz")
    private String street;
    
    @NotBlank(message = "Şehir alanı boş bırakılamaz")
    private String city;
    
    @NotBlank(message = "Ülke alanı boş bırakılamaz")
    private String country;
    
    @NotBlank(message = "Posta kodu boş bırakılamaz")
    private String postalCode;
}