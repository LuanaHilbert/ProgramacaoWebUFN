package com.luana.projeto1.service;

import com.luana.projeto1.dto.UpdatePhoneDTO;
import com.luana.projeto1.mapper.PhoneMapper;
import com.luana.projeto1.model.Phone;
import com.luana.projeto1.model.User;
import com.luana.projeto1.repository.PhoneRepository;
import com.luana.projeto1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;

    public PhoneService(PhoneRepository phoneRepository, UserRepository userRepository) {
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
    }

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public Optional<Phone> findById(Long id) {
        return phoneRepository.findById(id);
    }

    public void deleteById(Long id) {
        phoneRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return phoneRepository.existsById(id);
    }

    public Phone save(Long userId, Phone phone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));
        phone.setUser(user);
        return save(phone);
    }

    public Phone save(Phone phone) {
        // Limpa o número antes de salvar
        String cleanedNumber = phone.getNumber().replaceAll("[^0-9]", "");
        phone.setNumber(cleanedNumber);

        // Resto da lógica de validação e salvamento...
        return phoneRepository.save(phone);
    }

    private void validatePhone(Phone phone) {
        validatePhoneNumber(phone.getNumber());
        if (phone.getUser() == null) {
            throw new IllegalArgumentException("Telefone não associado a um usuário!");
        }
    }

    private void validateUserPhoneLimit(User user) {
        long count = phoneRepository.countByUser(user);
        if (count >= 3) {
            throw new IllegalArgumentException("Usuário já possui 3 telefones!");
        }
    }

    public void validatePhoneNumber(String formattedNumber) {
        if (formattedNumber == null) {
            throw new IllegalArgumentException("Número não pode ser nulo!");
        }

        // Remove todos os caracteres não numéricos
        String cleanedNumber = formattedNumber.replaceAll("[^0-9]", "");

        // Valida quantidade de dígitos (10 ou 11)
        if (cleanedNumber.length() < 10 || cleanedNumber.length() > 11) {
            throw new IllegalArgumentException(
                    "Número inválido! Use DDD + número (10 ou 11 dígitos). Ex: (11) 98765-4321"
            );
        }

        // Extrai DDD e valida
        int ddd = Integer.parseInt(cleanedNumber.substring(0, 2));
        if (ddd < 11 || ddd > 99) {
            throw new IllegalArgumentException("DDD inválido!");
        }
    }

    @Transactional
    public Phone updatePhone(Long id, UpdatePhoneDTO updateDTO) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Telefone não encontrado!"));

        // Validação do número (se fornecido)
        if (updateDTO.number() != null) {
            validatePhoneNumber(updateDTO.number());
        }

        // Validação de userId (se fornecido)
        if (updateDTO.userId() != null) {
            User newUser = userRepository.findById(updateDTO.userId())
                    .orElseThrow(() -> new IllegalArgumentException("Novo usuário não encontrado!"));

            if (phoneRepository.countByUser(newUser) >= 3) {
                throw new IllegalArgumentException("Novo usuário já possui 3 telefones!");
            }
            phone.setUser(newUser);
        }

        PhoneMapper.updateFromDTO(updateDTO, phone);
        return phoneRepository.save(phone);
    }
}