package com.restaurante.menuapi.service;

import com.restaurante.menuapi.dto.CreateOrderDTO;
import com.restaurante.menuapi.dto.OrderDTO;
import com.restaurante.menuapi.dto.OrderItemDTO;
import com.restaurante.menuapi.entity.Order;
import com.restaurante.menuapi.entity.OrderItem;
import com.restaurante.menuapi.entity.Product;
import com.restaurante.menuapi.repository.OrderRepository;
import com.restaurante.menuapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional; // Importar Optional

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        BigDecimal totalAmount = BigDecimal.ZERO;

        // É crucial inicializar a lista de itens do pedido na entidade Order
        order.setItems(new java.util.ArrayList<>());

        for (OrderItemDTO itemDTO : createOrderDTO.getItems()) {
            Optional<Product> productOptional = productRepository.findById(itemDTO.getProductId());
            if (!productOptional.isPresent()) {
                throw new RuntimeException("Product not found: " + itemDTO.getProductId());
            }
            Product product = productOptional.get();

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order); // Define a relação com o pedido atual
            orderItem.setQuantity(itemDTO.getQuantity());
            // Usa o preço atual do produto para garantir consistência
            orderItem.setPriceAtOrder(product.getPrice());

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
            order.getItems().add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para converter Entidade para DTO
    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream()
                    .map(item -> new OrderItemDTO(item.getProduct().getId(), item.getQuantity(), item.getPriceAtOrder(), item.getProduct().getName()))
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}