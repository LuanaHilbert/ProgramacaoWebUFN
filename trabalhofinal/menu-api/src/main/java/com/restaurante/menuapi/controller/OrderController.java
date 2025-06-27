package com.restaurante.menuapi.controller;

import com.restaurante.menuapi.dto.CreateOrderDTO;
import com.restaurante.menuapi.dto.OrderDTO;
import com.restaurante.menuapi.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173") // Permite requisições do frontend React (porta padrão do Vite)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST /api/orders - Finaliza uma compra (cria um novo pedido)
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        try {
            OrderDTO createdOrder = orderService.createOrder(createOrderDTO);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Exemplo simples de tratamento de erro para produto não encontrado
            return ResponseEntity.badRequest().body(null); // Pode retornar um DTO de erro mais detalhado
        }
    }

    // GET /api/orders/admin - Lista todos os pedidos (para área administrativa)
    @GetMapping("/admin")
    public ResponseEntity<List<OrderDTO>> getAllOrdersAdmin() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}