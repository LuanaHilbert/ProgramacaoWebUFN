// src/main/java/com/restaurante/menu/entity/OrderItem.java
package com.restaurante.menu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade que representa um item dentro de um pedido.
 * Mapeia para a tabela 'order_items' no banco de dados.
 */
@Entity
@Table(name = "order_items")
@Data // Gera getters, setters, toString, equals e hashCode via Lombok
@NoArgsConstructor // Gera construtor sem argumentos via Lombok
@AllArgsConstructor // Gera construtor com todos os argumentos via Lombok
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do item do pedido

    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento muitos-para-um com Order
    @JoinColumn(name = "order_id", nullable = false) // Coluna de chave estrangeira
    private Order order; // O pedido ao qual este item pertence

    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento muitos-para-um com Product
    @JoinColumn(name = "product_id", nullable = false) // Coluna de chave estrangeira
    private Product product; // O produto que foi pedido

    private Integer quantity; // Quantidade do produto pedido

    private BigDecimal priceAtOrder; // Preço do produto no momento do pedido (para evitar inconsistências se o preço do produto mudar)
}
