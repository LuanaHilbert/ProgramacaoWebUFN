package com.example.prova1.repository;

import com.example.prova1.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    @Query("SELECT v FROM Venda v WHERE v.cliente.id = :clienteId")
    List<Venda> findByClienteId(Long clienteId);
}