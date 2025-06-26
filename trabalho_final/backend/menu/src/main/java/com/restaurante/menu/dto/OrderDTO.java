// src/main/java/com/restaurante/menu/dto/OrderDTO.java
package com.restaurante.menu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para representar um pedido.
 * Utilizado para receber dados de pedidos do front-end e enviar informações de pedidos para o front-end.
 */
@Data // Gera getters, setters, toString, equals e hashCode via Lombok
@NoArgsConstructor // Gera construtor sem argumentos via Lombok
@AllArgsConstructor // Gera construtor com todos os argumentos via Lombok
public class OrderDTO {
    private Long id; // ID do pedido
    private LocalDateTime orderDate; // Data do pedido
    private BigDecimal totalAmount; // Valor total do pedido
    private List<CartItemDTO> items; // Lista de itens do pedido (usando CartItemDTO para simplicidade)
}
