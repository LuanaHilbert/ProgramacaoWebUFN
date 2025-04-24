package com.example.prova1.service;

import com.example.prova1.dto.ProdutoDTO;
import com.example.prova1.model.Produto;
import com.example.prova1.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;

    @Transactional
    public Produto create(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        return repository.save(produto);
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Produto findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void updateEstoque(Long id, Integer quantidade) {
        Produto produto = findById(id);
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        repository.save(produto);
    }
}
