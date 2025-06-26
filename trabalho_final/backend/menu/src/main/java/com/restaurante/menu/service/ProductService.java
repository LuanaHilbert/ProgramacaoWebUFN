// src/main/java/com/restaurante/menu/service/ProductService.java
package com.restaurante.menu.service;

import com.restaurante.menu.dto.ProductDTO;
import com.restaurante.menu.entity.Product;
import com.restaurante.menu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para gerenciar a lógica de negócio relacionada a produtos.
 * Realiza operações CRUD e mapeia entre entidades e DTOs.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Converte uma entidade Product para um ProductDTO.
     * @param product A entidade Product a ser convertida.
     * @return Um ProductDTO correspondente.
     */
    private ProductDTO convertToDto(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getImageUrl(),
                product.isActive()
        );
    }

    /**
     * Converte um ProductDTO para uma entidade Product.
     * @param productDTO O ProductDTO a ser convertido.
     * @return Uma entidade Product correspondente.
     */
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setActive(productDTO.isActive());
        return product;
    }

    /**
     * Cadastra um novo produto.
     * @param productDTO Dados do produto a ser cadastrado.
     * @return O DTO do produto cadastrado.
     */
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        // Garante que novos produtos sejam ativos por padrão, a menos que especificado
        if (productDTO.getId() == null) {
            product.setActive(true);
        }
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    /**
     * Atualiza um produto existente.
     * @param id O ID do produto a ser atualizado.
     * @param productDTO Os novos dados do produto.
     * @return O DTO do produto atualizado.
     * @throws EntityNotFoundException se o produto não for encontrado.
     */
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setCategory(productDTO.getCategory());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setImageUrl(productDTO.getImageUrl());
            existingProduct.setActive(productDTO.isActive()); // Permite atualizar o status
            Product updatedProduct = productRepository.save(existingProduct);
            return convertToDto(updatedProduct);
        }).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
    }

    /**
     * Ativa ou desativa um produto.
     * @param id O ID do produto.
     * @param active O status desejado (true para ativo, false para inativo).
     * @return O DTO do produto com o status atualizado.
     * @throws EntityNotFoundException se o produto não for encontrado.
     */
    public ProductDTO toggleProductStatus(Long id, boolean active) {
        return productRepository.findById(id).map(product -> {
            product.setActive(active);
            Product updatedProduct = productRepository.save(product);
            return convertToDto(updatedProduct);
        }).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
    }

    /**
     * Exclui um produto pelo ID.
     * @param id O ID do produto a ser excluído.
     * @throws EntityNotFoundException se o produto não for encontrado.
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado com ID: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Lista todos os produtos.
     * @return Uma lista de todos os DTOs de produtos.
     */
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os produtos ativos (para a área do cliente).
     * @return Uma lista de DTOs de produtos que estão ativos.
     */
    public List<ProductDTO> getActiveProducts() {
        return productRepository.findByActive(true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca um produto pelo ID.
     * @param id O ID do produto.
     * @return O DTO do produto encontrado.
     * @throws EntityNotFoundException se o produto não for encontrado.
     */
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
    }
}
