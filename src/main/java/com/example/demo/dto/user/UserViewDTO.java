package com.example.demo.dto.user;

import com.example.demo.dto.address.AddressViewDTO;
import lombok.Data;
import java.util.List;

@Data
public class UserViewDTO {
    private Long id;
    private String name;
    private String email;
    private List<AddressViewDTO> addresses;
}