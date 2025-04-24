package com.example.prova1.dto;

import java.time.LocalDateTime;
import java.util.List;

public record VendaResponseDTO(
        Long id,
        Long clienteId,
        String clienteNome,
        LocalDateTime dataVenda,
        Double desconto,
        Double valorTotal,
        List<ItemVendaResponseDTO> itens
) {}

record ItemVendaResponseDTO(
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        Double precoUnitario,
        Double subtotal
) {}