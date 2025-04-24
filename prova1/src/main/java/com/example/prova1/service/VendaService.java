package com.example.prova1.service;

import com.example.prova1.dto.*;
import com.example.prova1.exception.EstoqueInsuficienteException;
import com.example.prova1.model.*;
import com.example.prova1.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public VendaResponseDTO create(VendaDTO dto) {
        // Verificar estoque antes de processar a venda
        for (ItemVendaDTO item : dto.getItens()) {
            Produto produto = produtoService.findById(item.getProdutoId());
            if (produto.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto: " + produto.getNome());
            }
        }

        // Processar venda
        Venda venda = new Venda();
        venda.setCliente(clienteService.findById(dto.getClienteId()));
        venda.setDesconto(dto.getDesconto() != null ? dto.getDesconto() : 0.0);

        // Calcular itens e total
        List<ItemVenda> itens = dto.getItens().stream()
                .map(itemDto -> {
                    Produto produto = produtoService.findById(itemDto.getProdutoId());
                    ItemVenda item = new ItemVenda();
                    item.setProduto(produto);
                    item.setQuantidade(itemDto.getQuantidade());
                    item.setPrecoUnitario(itemDto.getPrecoUnitario());
                    item.setVenda(venda);

                    // Atualizar estoque
                    produtoService.updateEstoque(produto.getId(), itemDto.getQuantidade());

                    return item;
                })
                .collect(Collectors.toList());

        venda.setItens(itens);

        // Calcular valor total
        double subtotal = itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();

        double total = subtotal * (1 - venda.getDesconto() / 100);
        venda.setValorTotal(total);

        // Salvar venda
        Venda savedVenda = vendaRepository.save(venda);

        // Retornar DTO de resposta
        return toResponseDTO(savedVenda);
    }

    public List<VendaResponseDTO> findAll() {
        return vendaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public VendaResponseDTO findById(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        return toResponseDTO(venda);
    }

    public List<VendaResponseDTO> findVendasByClienteId(Long clienteId) {
        List<Venda> vendas = vendaRepository.findByClienteId(clienteId);
        return vendas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private VendaResponseDTO toResponseDTO(Venda venda) {
        List<ItemVendaResponseDTO> itensDTO = venda.getItens().stream()
                .map(item -> new ItemVendaResponseDTO(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getPrecoUnitario() * item.getQuantidade()
                ))
                .collect(Collectors.toList());

        return new VendaResponseDTO(
                venda.getId(),
                venda.getCliente().getId(),
                venda.getCliente().getNome(),
                venda.getDataVenda(),
                venda.getDesconto(),
                venda.getValorTotal(),
                itensDTO
        );
    }
}