package com.luana.projeto1.mapper;

import com.luana.projeto1.dto.PhoneDTO;
import com.luana.projeto1.dto.UserDTO;
import com.luana.projeto1.model.User;

import java.util.List;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        List<PhoneDTO> phones = user.getPhones().stream()
                .map(phone -> new PhoneDTO(
                        phone.getId(),
                        phone.getNumber(),
                        user.getId()
                ))
                .toList();

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                phones
        );
    }
}