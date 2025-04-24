package com.example.prova1.controller;

import com.example.prova1.dto.ProdutoDTO;
import com.example.prova1.model.Produto;
import com.example.prova1.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto create(@RequestBody ProdutoDTO dto) {
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

    @GetMapping("/estoque-menor-que")
    public List<Produto> getProdutosComEstoqueMenorQue(@RequestParam Integer quantidade) {
        return service.findProdutosComEstoqueMenorQue(quantidade);
    }

    @GetMapping("/buscar")
    public List<Produto> getProdutosByNome(@RequestParam String nome) {
        return service.findProdutosByNomeContaining(nome);
    }
}