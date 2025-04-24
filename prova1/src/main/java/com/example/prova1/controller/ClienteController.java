package com.example.prova1.controller;

import com.example.prova1.dto.ClienteDTO;
import com.example.prova1.model.Cliente;
import com.example.prova1.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody ClienteDTO dto) {
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