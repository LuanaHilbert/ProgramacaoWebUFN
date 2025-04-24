package com.example.prova1.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record VendaDTO(
        @NotNull Long clienteId,
        @PositiveOrZero Double desconto,
        @NotEmpty List<ItemVendaDTO> itens
) {}