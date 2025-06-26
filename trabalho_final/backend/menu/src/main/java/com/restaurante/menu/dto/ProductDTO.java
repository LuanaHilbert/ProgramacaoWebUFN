// src/main/java/com/restaurante/menu/dto/ProductDTO.java
package com.restaurante.menu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para produtos.
 * Utilizado para transferir dados de produtos entre as camadas da aplicação,
 * especialmente entre o Controller e o Service, e para a comunicação com o front-end.
 */
@Data // Gera getters, setters, toString, equals e hashCode via Lombok
@NoArgsConstructor // Gera construtor sem argumentos via Lombok
@AllArgsConstructor // Gera construtor com todos os argumentos via Lombok
public class ProductDTO {

    private Long id; // Identificador único do produto (pode ser nulo para criação)

    private String name; // Nome do produto

    private String description; // Descrição detalhada do produto

    private String category; // Categoria do produto

    private BigDecimal price; // Preço do produto

    private String imageUrl; // URL da imagem do produto

    private boolean active; // Status do produto (ativo/inativo)
}
