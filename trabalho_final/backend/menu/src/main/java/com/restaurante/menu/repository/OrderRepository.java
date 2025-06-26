// src/main/java/com/restaurante/menu/repository/OrderRepository.java
package com.restaurante.menu.repository;

import com.restaurante.menu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para a entidade Order.
 * Estende JpaRepository para fornecer métodos CRUD básicos para a entidade Order.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Métodos de consulta adicionais podem ser definidos aqui, se necessário.
    // Por exemplo: List<Order> findByCustomerName(String customerName);
}
