// src/main/java/com/restaurante/menu/controller/OrderController.java
package com.restaurante.menu.controller;

import com.restaurante.menu.dto.CartItemDTO;
import com.restaurante.menu.dto.OrderDTO;
import com.restaurante.menu.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller para gerenciar operações relacionadas a pedidos.
 * Define os endpoints da API para criação e listagem de pedidos.
 */
@RestController
@RequestMapping("/api/orders") // Prefixo para todos os endpoints deste controller
@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do front-end React
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Endpoint para criar um novo pedido (finalizar compra).
     * Recebe uma lista de CartItemDTOs do front-end.
     * @param cartItems Lista de itens no carrinho (productId e quantity).
     * @return ResponseEntity com o DTO do pedido criado e status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody List<CartItemDTO> cartItems) {
        try {
            OrderDTO createdOrder = orderService.createOrder(cartItems);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 404 se produto não existir
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 se carrinho vazio ou qty inválida
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 para outros erros
        }
    }

    /**
     * Endpoint para listar todos os pedidos (para a área administrativa, se necessário).
     * @return ResponseEntity com uma lista de DTOs de pedidos.
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Endpoint para buscar um pedido pelo ID.
     * @param id O ID do pedido.
     * @return ResponseEntity com o DTO do pedido e status HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        try {
            OrderDTO order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
