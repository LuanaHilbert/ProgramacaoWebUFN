package com.example.prova1.dto;

import jakarta.validation.constraints.*;

public record ItemVendaDTO(
        @NotNull Long produtoId,
        @Positive Integer quantidade,
        @Positive Double precoUnitario
) {}