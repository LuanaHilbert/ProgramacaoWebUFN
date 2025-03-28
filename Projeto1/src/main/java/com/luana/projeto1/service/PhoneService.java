package com.luana.projeto1.service;

import com.luana.projeto1.model.Phone;
import com.luana.projeto1.model.User;
import com.luana.projeto1.repository.PhoneRepository;
import com.luana.projeto1.repository.UserRepository;
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

    public Phone save(Phone phone) {
        if (phone.getUser() == null || phone.getUser().getId() == null) {
            throw new IllegalArgumentException("Usuário inválido!");
        }

        Optional<User> userOptional = userRepository.findById(phone.getUser().getId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        User user = userOptional.get();
        if (user.getPhones().size() >= 3) {
            throw new IllegalArgumentException("O usuário já possui o número máximo de 3 telefones!");
        }

        String number = phone.getNumber();
        if (number.length() < 2 || !number.matches("\\d{2}.*")) {
            throw new IllegalArgumentException("Número de telefone inválido!");
        }

        int ddd = Integer.parseInt(number.substring(0, 2));
        if (ddd < 11 || ddd > 99) {
            throw new IllegalArgumentException("DDD inválido! Deve estar entre 11 e 99.");
        }

        return phoneRepository.save(phone);
    }

    public void deleteById(Long id) {
        phoneRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return phoneRepository.existsById(id);
    }
}