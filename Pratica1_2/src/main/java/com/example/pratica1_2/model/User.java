package com.example.pratica1_2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users") // Evita conflito com palavra reservada
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // admin ou staff

    @OneToMany(mappedBy = "user")
    private List<Product> products;
}
