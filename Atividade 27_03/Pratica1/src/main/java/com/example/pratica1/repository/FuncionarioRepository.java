package com.example.pratica1.repository;

import com.example.pratica1.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {

        // Buscar por sobrenome
        List<Funcionario> findByUnome(String unome);

        // Buscar funcionários com salário acima de X
        List<Funcionario> findBySalarioGreaterThan(Double salario);

        // Buscar funcionários por supervisor
        List<Funcionario> findBySupervisorCpf(String cpfSupervisor);

}
