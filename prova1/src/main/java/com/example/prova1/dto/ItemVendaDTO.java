package com.example.prova1.dto;

public class ItemVendaDTO {
    private Long produtoId;
    private Integer quantidade;

    // Construtor
    public ItemVendaDTO(Long produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    // Getters
    public Long getProdutoId() {
        return produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

}