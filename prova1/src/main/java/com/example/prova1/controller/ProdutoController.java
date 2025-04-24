package com.example.prova1.controller;

import com.example.prova1.dto.ProdutoDTO;
import com.example.prova1.model.Produto;
import com.example.prova1.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto create(@RequestBody @Valid ProdutoDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<Produto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Produto findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
