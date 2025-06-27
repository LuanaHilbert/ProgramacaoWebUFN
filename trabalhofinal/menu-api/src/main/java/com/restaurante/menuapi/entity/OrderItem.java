package com.restaurante.menuapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento muitos-para-um com Product
    @JoinColumn(name = "product_id", nullable = false) // Coluna de chave estrangeira
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento muitos-para-um com Order
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Integer quantity; // Quantidade do produto no item do pedido

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtOrder; // Preço do produto no momento do pedido (pode mudar)
}