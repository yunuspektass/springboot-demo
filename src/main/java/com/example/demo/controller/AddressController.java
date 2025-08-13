package com.example.demo.controller;

import com.example.demo.dto.address.AddressCreateDTO;
import com.example.demo.dto.address.AddressViewDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AddressViewDTO> addAddress(
            @PathVariable Long userId,
            @Valid @RequestBody AddressCreateDTO addressCreateDTO) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + userId));

        Address address = modelMapper.map(addressCreateDTO, Address.class);
        user.addAddress(address);
        
        User savedUser = userRepository.save(user);
        Address savedAddress = savedUser.getAddresses().get(savedUser.getAddresses().size() - 1);
        
        return ResponseEntity.ok(modelMapper.map(savedAddress, AddressViewDTO.class));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + userId));

        Address address = user.getAddresses().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Adres bulunamadı: " + addressId));

        user.removeAddress(address);
        userRepository.save(user);
        
        return ResponseEntity.ok().build();
    }
}