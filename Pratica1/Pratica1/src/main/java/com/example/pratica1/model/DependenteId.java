package com.example.pratica1.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DependenteId implements Serializable {

    private String nome;
    private String funcionarioCpf;

    public DependenteId() {}

    public DependenteId(String nome, String funcionarioCpf) {
        this.nome = nome;
        this.funcionarioCpf = funcionarioCpf;
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    // equals e hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DependenteId)) return false;
        DependenteId that = (DependenteId) o;
        return Objects.equals(nome, that.nome) &&
                Objects.equals(funcionarioCpf, that.funcionarioCpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, funcionarioCpf);
    }
}

