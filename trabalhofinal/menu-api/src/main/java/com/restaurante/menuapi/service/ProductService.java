package com.restaurante.menuapi.service;

import com.restaurante.menuapi.dto.CreateProductDTO;
import com.restaurante.menuapi.dto.ProductDTO;
import com.restaurante.menuapi.entity.Product;
import com.restaurante.menuapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Marca a classe como um serviço Spring
public class ProductService {

    private final ProductRepository productRepository;

    // Injeção de dependência do ProductRepository
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Listar todos os produtos (para área administrativa)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO) // Converte Entidade para DTO
                .collect(Collectors.toList());
    }

    // Listar produtos ativos (para área do cliente)
    public List<ProductDTO> getActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obter um produto por ID
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Criar um novo produto
    @Transactional // Garante que a operação seja atômica
    public ProductDTO createProduct(CreateProductDTO createProductDTO) {
        Product product = new Product();
        product.setName(createProductDTO.getName());
        product.setDescription(createProductDTO.getDescription());
        product.setCategory(createProductDTO.getCategory());
        product.setPrice(createProductDTO.getPrice());
        product.setImageUrl(createProductDTO.getImageUrl());
        product.setActive(createProductDTO.isActive()); // Pode vir do DTO ou ser padrão

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // Atualizar um produto existente
    @Transactional
    public Optional<ProductDTO> updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDTO.getName());
                    existingProduct.setDescription(productDTO.getDescription());
                    existingProduct.setCategory(productDTO.getCategory());
                    existingProduct.setPrice(productDTO.getPrice());
                    existingProduct.setImageUrl(productDTO.getImageUrl());
                    existingProduct.setActive(productDTO.isActive());
                    Product updatedProduct = productRepository.save(existingProduct);
                    return convertToDTO(updatedProduct);
                });
    }

    // Deletar um produto
    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Ativar ou desativar um produto
    @Transactional
    public Optional<ProductDTO> toggleProductStatus(Long id, boolean active) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(active);
                    Product updatedProduct = productRepository.save(product);
                    return convertToDTO(updatedProduct);
                });
    }

    // Método auxiliar para converter Entidade para DTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.isActive());
        return dto;
    }
}