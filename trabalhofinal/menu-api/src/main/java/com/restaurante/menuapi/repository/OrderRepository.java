package com.restaurante.menuapi.repository;

import com.restaurante.menuapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}