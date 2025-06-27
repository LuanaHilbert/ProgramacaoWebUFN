package com.restaurante.menuapi.repository;

import com.restaurante.menuapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// JpaRepository fornece métodos CRUD prontos
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Método para encontrar produtos ativos
    List<Product> findByActiveTrue();
}