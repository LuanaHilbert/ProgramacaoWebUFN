package com.luana.projeto1.service;

import com.luana.projeto1.dto.CreateUserDTO;
import com.luana.projeto1.dto.UpdateUserDTO;
import com.luana.projeto1.dto.UserDTO;
import com.luana.projeto1.exception.ResourceNotFoundException;
import com.luana.projeto1.mapper.UserMapper;
import com.luana.projeto1.model.Phone;
import com.luana.projeto1.model.User;
import com.luana.projeto1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PhoneService phoneService;

    // Injetando PhoneService para uso no método createUser
    public UserService(
            UserRepository userRepository,
            PhoneService phoneService // Injetando PhoneService
    ) {
        this.userRepository = userRepository;
        this.phoneService = phoneService;
    }

    // Método para buscar todos os usuários
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Método para buscar usuário por ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // Método para buscar usuário por nome
    public List<User> findUsersByName(String name) {
        return userRepository.findByName(name);
    }

    // Método para buscar usuário por e-mail
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Método para buscar usuários por nome e e-mail
    public List<User> findUsersByNameAndEmail(String name, String email) {
        return userRepository.findByNameAndEmail(name, email);
    }

    // Método para buscar usuários por número de telefone
    public List<User> findUsersByPhoneNumber(String phoneNumber) {
        return userRepository.findAll().stream()
                .filter(user -> user.getPhones().stream()
                        .anyMatch(phone -> phone.getNumber().equals(phoneNumber)))
                .toList();
    }

    @Transactional
    public User createUser(CreateUserDTO userDTO) {
        // Validação de formato de e-mail
        if (!isValidEmail(userDTO.email())) {
            throw new IllegalArgumentException("Formato de e-mail inválido!");
        }

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
                Phone phone = new Phone();
                phone.setNumber(number);
                phone.setUser(savedUser);
                phoneService.save(phone);

                // Adicione o telefone à lista do usuário
                savedUser.getPhones().add(phone); // Lista já inicializada!
            }
        }

        return savedUser;
    }

    // Método para validar o formato do e-mail
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }

    // Método para verificar se o usuário existe pelo ID
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    // Método para deletar usuário pelo ID
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUser(Long id, UpdateUserDTO updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        // Validação de email único (se fornecido)
        if (updateDTO.email() != null && !updateDTO.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateDTO.email())) {
                throw new IllegalArgumentException("E-mail já está em uso!");
            }
        }

        UserMapper.updateFromDTO(updateDTO, user);
        return userRepository.save(user);
    }

    @Transactional
    public List<UserDTO> createUsers(List<CreateUserDTO> usersDTO) {
        return usersDTO.stream()
                .map(this::createUser)
                .map(UserMapper::toDTO)
                .toList();
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuário com o e-mail " + email + " não encontrado!");
        }
        userRepository.delete(user.get());
    }

    // Conta o total de usuários no banco
    public long countUsers() {
        return userRepository.count();
    }

    // Busca usuários por parte do nome
    public List<User> findUsersByPartOfName(String partOfName) {
        return userRepository.findByNameContaining(partOfName);
    }

    // Busca usuários cujo nome começa com o prefixo
    public List<User> findUsersByNameStartingWith(String prefix) {
        return userRepository.findByNameStartingWith(prefix);
    }

    // Retorna todos os usuários ordenados por nome
    public List<User> findAllUsersOrderedByName() {
        return userRepository.findAllByOrderByNameAsc();
    }

    // Retorna as últimas 3 pessoas ordenadas por nome decrescente
    public List<User> findLast3UsersOrderedByName() {
        return userRepository.findTop3ByOrderByNameDesc();
    }

    // Lista todos os usuários que têm apenas 1 telefone
    public List<User> findUsersWithOnePhone() {
        return userRepository.findByPhones_Size(1);
    }

    // Busca telefones por nome do usuário
    public List<Phone> findPhonesByUserName(String name) {
        return userRepository.findByName(name).stream()
                .flatMap(user -> user.getPhones().stream())
                .collect(Collectors.toList());
    }

}
