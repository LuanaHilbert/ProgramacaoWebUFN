package com.example.pratica1_2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String paymentMethod; // cartão de crédito, débito, dinheiro
    private BigDecimal transactionAmount;
    private String status; // pago, pendente
    private String transactionId;
}
