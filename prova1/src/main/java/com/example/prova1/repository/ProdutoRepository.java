package com.example.prova1.repository;

import com.example.prova1.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque < :quantidade")
    List<Produto> findByQuantidadeEstoqueLessThan(Integer quantidade);

    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Produto> findByNomeContainingIgnoreCase(String keyword);
}