package com.luana.projeto1.dto;

import java.util.List;

public record CreateUserDTO(
        String name,
        String email,
        List<String> phones // Lista de números (sem ID ou userId)
) {}