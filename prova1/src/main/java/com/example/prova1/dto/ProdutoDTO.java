package com.example.prova1.dto;

public class ProdutoDTO {
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidadeEstoque;

    // Construtor
    public ProdutoDTO(String nome, String descricao, Double preco, Integer quantidadeEstoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
}