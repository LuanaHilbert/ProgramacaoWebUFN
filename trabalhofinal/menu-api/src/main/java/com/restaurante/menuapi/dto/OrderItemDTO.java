package com.restaurante.menuapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private Integer quantity;
    private BigDecimal priceAtOrder; // Opcional, pode ser calculado no backend
    private String productName; // Adicionado para facilitar a visualização no frontend
}