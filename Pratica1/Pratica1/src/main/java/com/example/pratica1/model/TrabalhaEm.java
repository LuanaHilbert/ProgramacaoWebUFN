package com.example.pratica1.model;

import jakarta.persistence.*;

@Entity
public class TrabalhaEm {

    @EmbeddedId
    private TrabalhaEmId id;

    @ManyToOne
    @MapsId("funcionarioCpf")
    @JoinColumn(name = "cpf_funcionario")
    private Funcionario funcionario;

    @ManyToOne
    @MapsId("projetoPnumero")
    @JoinColumn(name = "pnumero_projeto")
    private Projeto projeto;

    @Column(nullable = false)
    private Double horas;

    public TrabalhaEm() {}

    public TrabalhaEmId getId() {
        return id;
    }

    public void setId(TrabalhaEmId id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }
}
