package com.example.prova1.dto;

public class ItemVendaResponseDTO {
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private Double precoUnitario;
    private Double subtotal;

    public ItemVendaResponseDTO(Long produtoId, String produtoNome, Integer quantidade,
                                Double precoUnitario, Double subtotal) {
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }

    // Getters
    public Long getProdutoId() {
        return produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }
}