package com.example.demo.dto.address;

import lombok.Data;

@Data
public class AddressViewDTO {
    private Long id;
    private String street;
    private String city;
    private String country;
    private String postalCode;
}