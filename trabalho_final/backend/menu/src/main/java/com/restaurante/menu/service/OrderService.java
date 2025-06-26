// src/main/java/com/restaurante/menu/service/OrderService.java
package com.restaurante.menu.service;

import com.restaurante.menu.dto.CartItemDTO;
import com.restaurante.menu.dto.OrderDTO;
import com.restaurante.menu.entity.Order;
import com.restaurante.menu.entity.OrderItem;
import com.restaurante.menu.entity.Product;
import com.restaurante.menu.repository.OrderRepository;
import com.restaurante.menu.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para gerenciar a lógica de negócio relacionada a pedidos.
 * Realiza a criação de pedidos e o cálculo do valor total.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Cria um novo pedido com base nos itens do carrinho fornecidos.
     * @param cartItems Lista de CartItemDTOs contendo o productId e quantity.
     * @return O DTO do pedido criado.
     * @throws EntityNotFoundException se algum produto no carrinho não for encontrado.
     * @throws IllegalArgumentException se a quantidade de algum item for inválida.
     */
    @Transactional // Garante que toda a operação seja atômica
    public OrderDTO createOrder(List<CartItemDTO> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("O carrinho de compras não pode estar vazio.");
        }

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemDTO cartItemDTO : cartItems) {
            Product product = productRepository.findById(cartItemDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + cartItemDTO.getProductId() + " não encontrado."));

            if (!product.isActive()) {
                throw new IllegalArgumentException("Produto '" + product.getName() + "' não está ativo e não pode ser adicionado ao pedido.");
            }

            if (cartItemDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantidade inválida para o produto " + product.getName() + ": " + cartItemDTO.getQuantity());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItemDTO.getQuantity());
            orderItem.setPriceAtOrder(product.getPrice()); // Captura o preço do produto no momento do pedido
            orderItem.setOrder(order); // Associa o item ao pedido

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(cartItemDTO.getQuantity())));
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return convertToDto(savedOrder);
    }

    /**
     * Converte uma entidade Order para um OrderDTO.
     * @param order A entidade Order a ser convertida.
     * @return Um OrderDTO correspondente.
     */
    private OrderDTO convertToDto(Order order) {
        List<CartItemDTO> itemDTOs = order.getItems().stream()
                .map(item -> new CartItemDTO(item.getProduct().getId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                itemDTOs
        );
    }

    /**
     * Lista todos os pedidos existentes.
     * @return Uma lista de DTOs de pedidos.
     */
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca um pedido pelo ID.
     * @param id O ID do pedido.
     * @return O DTO do pedido encontrado.
     * @throws EntityNotFoundException se o pedido não for encontrado.
     */
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));
    }
}
