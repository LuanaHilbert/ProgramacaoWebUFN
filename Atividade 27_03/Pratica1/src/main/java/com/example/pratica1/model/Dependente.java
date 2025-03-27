package com.example.pratica1.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Dependente {

    @EmbeddedId
    private DependenteId id;

    @Column(length = 1)
    private String sexo;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNasc;

    @Column(nullable = false)
    private String parentesco;

    @MapsId("funcionarioCpf")
    @ManyToOne
    @JoinColumn(name = "cpf_funcionario", nullable = false)
    private Funcionario funcionario;

    public Dependente() {}

    // Getters e Setters

    public DependenteId getId() {
        return id;
    }

    public void setId(DependenteId id) {
        this.id = id;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
