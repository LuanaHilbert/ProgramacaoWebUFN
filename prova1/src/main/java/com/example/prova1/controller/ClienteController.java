package com.example.prova1.controller;

import com.example.prova1.dto.ClienteDTO;
import com.example.prova1.model.Cliente;
import com.example.prova1.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody @Valid ClienteDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<Cliente> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
