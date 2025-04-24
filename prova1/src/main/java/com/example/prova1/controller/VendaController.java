package com.example.prova1.controller;

import com.example.prova1.dto.VendaDTO;
import com.example.prova1.dto.VendaResponseDTO;
import com.example.prova1.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    @Autowired
    private VendaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendaResponseDTO create(@RequestBody VendaDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<VendaResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public VendaResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<VendaResponseDTO> getVendasByClienteId(@PathVariable Long clienteId) {
        return service.findVendasByClienteId(clienteId);
    }
}