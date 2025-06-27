package com.restaurante.menuapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity // Marca a classe como uma entidade JPA
@Table(name = "products") // Nome da tabela no banco de dados
@Data // Gera getters, setters, toString, equals e hashCode (Lombok)
@NoArgsConstructor // Construtor sem argumentos (Lombok)
@AllArgsConstructor // Construtor com todos os argumentos (Lombok)
public class Product {
    @Id // Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID
    private Long id;

    @Column(nullable = false, length = 100) // Coluna não nula, com comprimento máximo
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, precision = 10, scale = 2) // Precisão para valores monetários
    private BigDecimal price;

    @Column(length = 255)
    private String imageUrl; // URL da imagem do produto

    @Column(nullable = false)
    private boolean active = true; // Status do produto (ativo/inativo), padrão é ativo
}