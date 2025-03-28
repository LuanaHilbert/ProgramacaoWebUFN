package com.luana.projeto1.repository;

import com.luana.projeto1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsById(Long id); // Adicionando este método
}

