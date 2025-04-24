package com.example.prova1.service;

import com.example.prova1.dto.ClienteDTO;
import com.example.prova1.exception.ClienteAlreadyExistsException;
import com.example.prova1.model.Cliente;
import com.example.prova1.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Transactional
    public Cliente create(ClienteDTO dto) {
        if (repository.existsByCpf(dto.getCpf())) {
            throw new ClienteAlreadyExistsException("CPF já cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());

        return repository.save(cliente);
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
}