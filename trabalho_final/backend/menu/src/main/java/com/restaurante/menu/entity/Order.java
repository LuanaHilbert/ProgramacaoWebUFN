// src/main/java/com/restaurante/menu/entity/Order.java
package com.restaurante.menu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade que representa um pedido (compra) de um cliente.
 * Mapeia para a tabela 'orders' no banco de dados.
 */
@Entity
@Table(name = "orders")
@Data // Gera getters, setters, toString, equals e hashCode via Lombok
@NoArgsConstructor // Gera construtor sem argumentos via Lombok
@AllArgsConstructor // Gera construtor com todos os argumentos via Lombok
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do pedido

    private LocalDateTime orderDate; // Data e hora em que o pedido foi feito

    private BigDecimal totalAmount; // Valor total do pedido

    // Relacionamento um-para-muitos com OrderItem
    // CascadeType.ALL significa que operações no Order (salvar, remover)
    // serão propagadas para os OrderItems associados.
    // orphanRemoval = true garante que OrderItems órfãos (desvinculados de um pedido) sejam removidos.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items; // Lista de itens incluídos no pedido

    // Poderíamos adicionar campos para informações do cliente se necessário,
    // como 'customerName', 'customerEmail', 'deliveryAddress', etc.
    // Por simplicidade, estes não foram incluídos no escopo atual.

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now(); // Define a data do pedido automaticamente na criação
    }
}
