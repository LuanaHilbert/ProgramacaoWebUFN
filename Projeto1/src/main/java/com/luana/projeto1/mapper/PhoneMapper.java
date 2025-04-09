package com.luana.projeto1.mapper;

import com.luana.projeto1.dto.CreatePhoneDTO;
import com.luana.projeto1.dto.PhoneDTO;
import com.luana.projeto1.dto.UpdatePhoneDTO;
import com.luana.projeto1.model.Phone;

public class PhoneMapper {

    // Converte CreatePhoneDTO para entidade Phone
    public static Phone toEntity(CreatePhoneDTO dto) {
        Phone phone = new Phone();
        phone.setNumber(dto.number());
        return phone;
    }

    // Converte entidade Phone para PhoneDTO
    public static PhoneDTO toDTO(Phone phone) {
        return new PhoneDTO(
                phone.getId(),
                phone.getNumber(),
                phone.getUser() != null ? phone.getUser().getId() : null
        );
    }

    public static void updateFromDTO(UpdatePhoneDTO dto, Phone phone) {
        if (dto.number() != null) {
            phone.setNumber(dto.number());
        }
    }
}