package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String name;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    
    // Helper method to add address
    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }
    
    // Helper method to remove address
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null);
    }
}