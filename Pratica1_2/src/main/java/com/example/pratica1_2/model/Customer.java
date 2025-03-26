package com.example.pratica1_2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;  // Para autenticação

    @OneToMany(mappedBy = "customer")
    private List<Order> orderHistory;
}