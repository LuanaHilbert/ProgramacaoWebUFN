package com.example.prova1.dto;

public class ClienteDTO {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    // Construtor
    public ClienteDTO(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}