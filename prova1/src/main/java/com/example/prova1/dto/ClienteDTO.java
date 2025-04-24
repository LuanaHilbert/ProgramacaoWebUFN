package com.example.prova1.dto;

import jakarta.validation.constraints.*;

public record ClienteDTO(
        @NotBlank String nome,
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotBlank @Email String email,
        @NotBlank String telefone
) {}
