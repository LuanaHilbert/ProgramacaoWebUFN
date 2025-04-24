package com.example.prova1.service;

import com.example.prova1.dto.*;
import com.example.prova1.exception.EstoqueInsuficienteException;
import com.example.prova1.model.*;
import com.example.prova1.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    @Transactional
    public VendaResponseDTO create(VendaDTO dto) {
        // Verificar estoque antes de processar a venda
        for (ItemVendaDTO item : dto.itens()) {
            Produto produto = produtoService.findById(item.produtoId());
            if (produto.getQuantidadeEstoque() < item.quantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto: " + produto.getNome());
            }
        }

        // Processar venda
        Venda venda = new Venda();
        venda.setCliente(clienteService.findById(dto.clienteId()));
        venda.setDesconto(dto.desconto() != null ? dto.desconto() : 0.0);

        // Calcular itens e total
        List<ItemVenda> itens = dto.itens().stream()
                .map(itemDto -> {
                    Produto produto = produtoService.findById(itemDto.produtoId());
                    ItemVenda item = new ItemVenda();
                    item.setProduto(produto);
                    item.setQuantidade(itemDto.quantidade());
                    item.setPrecoUnitario(itemDto.precoUnitario());
                    item.setVenda(venda);

                    // Atualizar estoque
                    produtoService.updateEstoque(produto.getId(), itemDto.quantidade());

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
        Venda venda = vendaRepository.findById(id).orElseThrow();
        return toResponseDTO(venda);
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