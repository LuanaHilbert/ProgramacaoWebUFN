package com.example.prova1.dto;

import java.util.List;

public class VendaDTO {
    private Long clienteId;
    private Double desconto;
    private List<ItemVendaDTO> itens;

    // Construtor
    public VendaDTO(Long clienteId, Double desconto, List<ItemVendaDTO> itens) {
        this.clienteId = clienteId;
        this.desconto = desconto;
        this.itens = itens;
    }

    // Getters
    public Long getClienteId() {
        return clienteId;
    }

    public Double getDesconto() {
        return desconto;
    }

    public List<ItemVendaDTO> getItens() {
        return itens;
    }
}