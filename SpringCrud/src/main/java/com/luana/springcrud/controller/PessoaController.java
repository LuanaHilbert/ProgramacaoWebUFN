package com.luana.springcrud.controller;

import com.luana.springcrud.model.Pessoa;
import com.luana.springcrud.repository.PessoaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {


    private final PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    //listar todas as pessoas
    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    //buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable int id) {
        return pessoaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Buscar por cpf
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Pessoa> buscarPorCpf(@PathVariable String cpf) {
        return pessoaRepository.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Cria uma nova pessoa
    @PostMapping
    public @ResponseBody Pessoa salvar (@RequestBody Pessoa pessoa) {
        pessoaRepository.save(pessoa);
        return pessoa;
    }

    //Atualizar uma pessoa
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar (@PathVariable int id,  @RequestBody Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com id: " + id));
        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setCpf(pessoaAtualizada.getCpf());
        pessoa.setEmail(pessoaAtualizada.getEmail());
        pessoaRepository.save(pessoa);
        return ResponseEntity.ok(pessoa);
    }

    //Deletar uma pessoa
    @DeleteMapping("/{id}")
    public ResponseEntity<Pessoa> remover (@PathVariable int id) {
        if (pessoaRepository.existsById(id)) {
            pessoaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
