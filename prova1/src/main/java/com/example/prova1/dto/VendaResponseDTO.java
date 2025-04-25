package com.example.prova1.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VendaResponseDTO {
    private Long id;
    private Long clienteId;
    private String clienteNome;
    private LocalDateTime dataVenda;
    private Double descontoPercentual;  // Renomeado para ficar claro
    private Double descontoEmValor;    // Novo campo
    private Double valorTotal;
    private List<ItemVendaResponseDTO> itens;

    public VendaResponseDTO(Long id, Long clienteId, String clienteNome, LocalDateTime dataVenda,
                            Double descontoPercentual, Double descontoEmValor, Double valorTotal,
                            List<ItemVendaResponseDTO> itens) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.dataVenda = dataVenda;
        this.descontoPercentual = descontoPercentual;
        this.descontoEmValor = descontoEmValor;
        this.valorTotal = valorTotal;
        this.itens = itens;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public Double getDescontoPercentual() {
        return descontoPercentual;
    }

    public Double getDescontoEmValor() {
        return descontoEmValor;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public List<ItemVendaResponseDTO> getItens() {
        return itens;
    }
}