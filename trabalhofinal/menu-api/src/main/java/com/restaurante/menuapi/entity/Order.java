package com.restaurante.menuapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderDate; // Data e hora do pedido

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount; // Valor total do pedido

    // Relacionamento um-para-muitos com OrderItem
    // cascade = CascadeType.ALL: Operações no Order (ex: persist, remove) serão aplicadas aos OrderItems associados
    // orphanRemoval = true: Se um OrderItem for removido da lista, ele será deletado do DB
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>(); // Inicializar a lista para evitar NullPointerException
}