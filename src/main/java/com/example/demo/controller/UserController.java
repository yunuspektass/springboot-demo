package com.example.demo.controller;

import com.example.demo.dto.user.UserCreateDTO;
import com.example.demo.dto.user.UserUpdateDTO;
import com.example.demo.dto.user.UserViewDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<UserViewDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserViewDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserViewDTO> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + id));
        return ResponseEntity.ok(modelMapper.map(user, UserViewDTO.class));
    }

    @PostMapping
    public UserViewDTO createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserViewDTO.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserViewDTO> updateUser(@PathVariable Long id,
                                                  @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + id));

        modelMapper.map(userUpdateDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(modelMapper.map(updatedUser, UserViewDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Kullanıcı bulunamadı: " + id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}