// src/main/java/com/restaurante/menu/controller/ProductController.java
package com.restaurante.menu.controller;

import com.restaurante.menu.dto.ProductDTO;
import com.restaurante.menu.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller para gerenciar operações relacionadas a produtos.
 * Define os endpoints da API para CRUD de produtos e alternância de status.
 */
@RestController
@RequestMapping("/api/products") // Prefixo para todos os endpoints deste controller
@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do front-end React
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Endpoint para criar um novo produto.
     * @param productDTO Dados do produto a ser criado.
     * @return ResponseEntity com o DTO do produto criado e status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Endpoint para atualizar um produto existente.
     * @param id O ID do produto a ser atualizado.
     * @param productDTO Os novos dados do produto.
     * @return ResponseEntity com o DTO do produto atualizado e status HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o produto não for encontrado
        }
    }

    /**
     * Endpoint para alternar o status de um produto (ativo/inativo).
     * @param id O ID do produto.
     * @param active Booleano indicando o status desejado (true para ativo, false para inativo).
     * @return ResponseEntity com o DTO do produto atualizado e status HTTP 200 (OK).
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductDTO> toggleProductStatus(@PathVariable Long id, @RequestParam boolean active) {
        try {
            ProductDTO updatedProduct = productService.toggleProductStatus(id, active);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para excluir um produto.
     * @param id O ID do produto a ser excluído.
     * @return ResponseEntity com status HTTP 204 (No Content) se a exclusão for bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build(); // Retorna 204 após exclusão bem-sucedida
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para listar todos os produtos (para a área administrativa).
     * @return ResponseEntity com uma lista de todos os DTOs de produtos.
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint para listar apenas os produtos ativos (para a área do cliente).
     * @return ResponseEntity com uma lista de DTOs de produtos ativos.
     */
    @GetMapping("/active")
    public ResponseEntity<List<ProductDTO>> getActiveProducts() {
        List<ProductDTO> activeProducts = productService.getActiveProducts();
        return ResponseEntity.ok(activeProducts);
    }

    /**
     * Endpoint para buscar um produto pelo ID.
     * @param id O ID do produto.
     * @return ResponseEntity com o DTO do produto e status HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        try {
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
