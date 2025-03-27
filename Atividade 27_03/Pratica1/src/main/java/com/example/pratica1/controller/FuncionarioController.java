package com.example.pratica1.controller;

import com.example.pratica1.model.Funcionario;
import com.example.pratica1.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Criar funcionário
    @PostMapping
    public Funcionario criarFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    // Listar todos
    @GetMapping
    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    // Buscar por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Funcionario> buscarPorCpf(@PathVariable String cpf) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(cpf);
        return funcionario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar funcionário
    @PutMapping("/{cpf}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable String cpf, @RequestBody Funcionario funcionarioAtualizado) {
        return funcionarioRepository.findById(cpf)
                .map(funcionario -> {
                    funcionario.setPnome(funcionarioAtualizado.getPnome());
                    funcionario.setMinicial(funcionarioAtualizado.getMinicial());
                    funcionario.setUnome(funcionarioAtualizado.getUnome());
                    funcionario.setDataNasc(funcionarioAtualizado.getDataNasc());
                    funcionario.setSalario(funcionarioAtualizado.getSalario());
                    funcionario.setSexo(funcionarioAtualizado.getSexo());
                    funcionario.setEndereco(funcionarioAtualizado.getEndereco());
                    funcionario.setSupervisor(funcionarioAtualizado.getSupervisor());
                    funcionarioRepository.save(funcionario);
                    return ResponseEntity.ok(funcionario);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar funcionário
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable String cpf) {
        if (funcionarioRepository.existsById(cpf)) {
            funcionarioRepository.deleteById(cpf);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Buscar por supervisor (extra)
    @GetMapping("/supervisor/{cpfSupervisor}")
    public List<Funcionario> listarPorSupervisor(@PathVariable String cpfSupervisor) {
        return funcionarioRepository.findBySupervisorCpf(cpfSupervisor);
    }
}
