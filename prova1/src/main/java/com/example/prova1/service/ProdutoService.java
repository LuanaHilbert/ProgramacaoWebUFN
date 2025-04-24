package com.example.prova1.service;

import com.example.prova1.dto.ProdutoDTO;
import com.example.prova1.model.Produto;
import com.example.prova1.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Produto create(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        return repository.save(produto);
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Produto findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Transactional
    public void updateEstoque(Long id, Integer quantidade) {
        Produto produto = findById(id);
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        repository.save(produto);
    }

    public List<Produto> findProdutosComEstoqueMenorQue(Integer quantidade) {
        return repository.findByQuantidadeEstoqueLessThan(quantidade);
    }

    public List<Produto> findProdutosByNomeContaining(String keyword) {
        return repository.findByNomeContainingIgnoreCase(keyword);
    }
}