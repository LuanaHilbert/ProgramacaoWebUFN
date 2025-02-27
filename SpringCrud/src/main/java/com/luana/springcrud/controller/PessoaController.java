package com.luana.springcrud.controller;

import com.luana.springcrud.model.Pessoa;
import com.luana.springcrud.repository.PessoaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {


    private final PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    //Cria uma nova pessoa
    @PostMapping
    public @ResponseBody Pessoa salvar (@RequestBody Pessoa pessoa) {
        pessoaRepository.save(pessoa);
        return pessoa;
    }
}
