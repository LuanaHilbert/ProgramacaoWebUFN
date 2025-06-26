// src/main/java/com/restaurante/menu/entity/Product.java
package com.restaurante.menu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade que representa um produto no cardápio do restaurante.
 * Mapeia para a tabela 'products' no banco de dados.
 */
@Entity
@Table(name = "products")
@Data // Gera getters, setters, toString, equals e hashCode via Lombok
@NoArgsConstructor // Gera construtor sem argumentos via Lombok
@AllArgsConstructor // Gera construtor com todos os argumentos via Lombok
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do produto

    private String name; // Nome do produto

    private String description; // Descrição detalhada do produto

    private String category; // Categoria do produto (ex: "Prato Principal", "Bebida", "Sobremesa")

    private BigDecimal price; // Preço do produto

    private String imageUrl; // URL da imagem do produto

    private boolean active = true; // Status do produto (ativo/inativo), padrão é ativo
}
