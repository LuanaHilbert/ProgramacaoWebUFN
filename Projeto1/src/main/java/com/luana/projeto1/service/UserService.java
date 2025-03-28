package com.luana.projeto1.service;

import com.luana.projeto1.dto.CreateUserDTO;
import com.luana.projeto1.dto.UserRecord;
import com.luana.projeto1.exception.ResourceNotFoundException;
import com.luana.projeto1.model.Phone;
import com.luana.projeto1.model.User;
import com.luana.projeto1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PhoneService phoneService;

    public UserService(
            UserRepository userRepository,
            PhoneService phoneService // Injetando PhoneService
    ) {
        this.userRepository = userRepository;
        this.phoneService = phoneService;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User createUser(CreateUserDTO userDTO) {
        // Validação de email único
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("E-mail já está em uso!");
        }

        // Validação de limite de telefones
        if (userDTO.phones() != null && userDTO.phones().size() > 3) {
            throw new IllegalArgumentException("Máximo de 3 telefones por usuário!");
        }

        // Cria e salva o usuário
        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        User savedUser = userRepository.save(user);

        // Adiciona telefones (se houver)
        if (userDTO.phones() != null) {
            for (String number : userDTO.phones()) {
                // Validação do número via PhoneService
                phoneService.validatePhoneNumber(number);

                Phone phone = new Phone();
                phone.setNumber(number);
                phone.setUser(savedUser);
                // Dentro do createUser:
                phoneService.save(phone); // Usa o novo método save(Phone)
            }
        }

        return savedUser;
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, CreateUserDTO userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (userDetails.email() != null && !userDetails.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDetails.email())) {
                throw new IllegalArgumentException("E-mail já está em uso!");
            }
            user.setEmail(userDetails.email());
        }

        if (userDetails.name() != null) {
            user.setName(userDetails.name());
        }

        return userRepository.save(user);
    }

}
