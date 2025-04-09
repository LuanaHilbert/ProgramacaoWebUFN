package com.luana.projeto1.dto;

public record UpdatePhoneDTO(
        String number,   // Campo opcional
        Long userId      // Campo opcional (para transferir o telefone para outro usuário)
) {}