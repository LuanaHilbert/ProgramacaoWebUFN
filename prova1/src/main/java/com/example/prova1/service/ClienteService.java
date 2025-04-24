package com.example.prova1.service;

import com.example.prova1.dto.ClienteDTO;
import com.example.prova1.exception.ClienteAlreadyExistsException;
import com.example.prova1.model.Cliente;
import com.example.prova1.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    @Transactional
    public Cliente create(ClienteDTO dto) {
        if (repository.existsByCpf(dto.cpf())) {
            throw new ClienteAlreadyExistsException("CPF já cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());

        return repository.save(cliente);
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
