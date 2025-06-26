// src/main/java/com/restaurante/menu/repository/ProductRepository.java
package com.restaurante.menu.repository;

import com.restaurante.menu.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de repositório para a entidade Product.
 * Estende JpaRepository para fornecer métodos CRUD básicos
 * e consultas personalizadas para a entidade Product.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Encontra todos os produtos que estão ativos.
     * @param active Booleano para filtrar produtos ativos (true) ou inativos (false).
     * @return Uma lista de produtos com o status especificado.
     */
    List<Product> findByActive(boolean active);
}
