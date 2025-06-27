package com.restaurante.menuapi.controller;

import com.restaurante.menuapi.dto.CreateProductDTO;
import com.restaurante.menuapi.dto.ProductDTO;
import com.restaurante.menuapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marca a classe como um controlador REST
@RequestMapping("/api/products") // Define o caminho base para os endpoints
@CrossOrigin(origins = "http://localhost:5173") // Permite requisições do frontend React (porta padrão do Vite)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ENDPOINTS PARA A ÁREA ADMINISTRATIVA

    // GET /api/products/admin - Lista todos os produtos (ativos e inativos)
    @GetMapping("/admin")
    public ResponseEntity<List<ProductDTO>> getAllProductsAdmin() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // GET /api/products/{id} - Obtém um produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/products - Cadastra um novo produto
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // PUT /api/products/{id} - Atualiza um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/products/{id} - Exclui um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH /api/products/{id}/status - Altera o status de ativo/inativo
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductDTO> toggleProductStatus(@PathVariable Long id, @RequestParam boolean active) {
        return productService.toggleProductStatus(id, active)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ENDPOINTS PARA A ÁREA DO CLIENTE

    // GET /api/products - Lista apenas os produtos ativos
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getActiveProductsClient() {
        List<ProductDTO> products = productService.getActiveProducts();
        return ResponseEntity.ok(products);
    }
}