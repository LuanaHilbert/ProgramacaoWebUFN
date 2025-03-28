package com.luana.projeto1.dto;

import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        List<PhoneDTO> phones // Lista de PhoneDTO associados
) {}