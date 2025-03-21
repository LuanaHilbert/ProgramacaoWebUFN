package com.luana.projeto1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Phone> Phones;


}
