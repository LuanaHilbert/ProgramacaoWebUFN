// src/main/java/com/restaurante/menu/dto/CartItemDTO.java
package com.restaurante.menu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para representar um item no carrinho de compras.
 * Usado pelo front-end para enviar a lista de produtos e suas quantidades
 * ao back-end para finalizar um pedido.
 */
@Data // Gera getters, setters, toString, equals e hashCode via Lombok
@NoArgsConstructor // Gera construtor sem argumentos via Lombok
@AllArgsConstructor // Gera construtor com todos os argumentos via Lombok
public class CartItemDTO {
    private Long productId; // ID do produto no carrinho
    private Integer quantity; // Quantidade do produto no carrinho
}
