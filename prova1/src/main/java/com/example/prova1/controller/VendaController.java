package com.example.prova1.controller;

import com.example.prova1.dto.VendaDTO;
import com.example.prova1.dto.VendaResponseDTO;
import com.example.prova1.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendaResponseDTO create(@RequestBody @Valid VendaDTO dto) {
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
}
