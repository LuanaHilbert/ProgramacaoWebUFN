package com.luana.projeto1.dto;

import java.util.List;

public record UserRecord(
        String name,
        String email,
        List<PhoneRecord> phones
) {}