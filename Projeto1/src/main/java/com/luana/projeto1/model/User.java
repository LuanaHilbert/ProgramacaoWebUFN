package com.luana.projeto1.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Phone> phones = new ArrayList<>();

    @Builder
    public User(String name, String email, List<Phone> phones) {
        this.name = name;
        this.email = email;
        this.phones = phones != null ? phones : new ArrayList<>(); // Garante inicialização
    }

    public User() {
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Phone> getPhones() { return phones; }
    public void setPhones(List<Phone> phones) { this.phones = phones; }
}
