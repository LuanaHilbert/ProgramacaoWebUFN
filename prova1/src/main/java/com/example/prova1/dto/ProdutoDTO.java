package com.example.prova1.dto;

import jakarta.validation.constraints.*;

public record ProdutoDTO(
        @NotBlank String nome,
        @NotBlank String descricao,
        @Positive Double preco,
        @PositiveOrZero Integer quantidadeEstoque
) {}