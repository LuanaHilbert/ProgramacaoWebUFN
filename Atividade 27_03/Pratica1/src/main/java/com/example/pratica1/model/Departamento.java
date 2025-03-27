package com.example.pratica1.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Departamento {

    @Id
    @Column(name = "dnumero")
    private Integer dnumero;

    @Column(name = "dnome", nullable = false, unique = true)
    private String dnome;

    // 1:N com localizações (simplesmente uma lista de strings)
    @ElementCollection
    @CollectionTable(name = "localizacoes_departamento", joinColumns = @JoinColumn(name = "dnumero"))
    @Column(name = "localizacao")
    private List<String> localizacoes;

    // 1:N com Funcionarios (trabalham neste departamento)
    @OneToMany(mappedBy = "departamento")
    private List<Funcionario> funcionarios;

    // 1:1 com Funcionario (gerente)
    @OneToOne
    @JoinColumn(name = "cpf_gerente", unique = true)
    private Funcionario gerente;

    @Column(name = "data_inicio_gerente")
    private LocalDate dataInicioGerente;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private List<Projeto> projetos;

    // Getters e Setters

    public Integer getDnumero() {
        return dnumero;
    }

    public void setDnumero(Integer dnumero) {
        this.dnumero = dnumero;
    }

    public String getDnome() {
        return dnome;
    }

    public void setDnome(String dnome) {
        this.dnome = dnome;
    }

    public List<String> getLocalizacoes() {
        return localizacoes;
    }

    public void setLocalizacoes(List<String> localizacoes) {
        this.localizacoes = localizacoes;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Funcionario getGerente() {
        return gerente;
    }

    public void setGerente(Funcionario gerente) {
        this.gerente = gerente;
    }

    public LocalDate getDataInicioGerente() {
        return dataInicioGerente;
    }

    public void setDataInicioGerente(LocalDate dataInicioGerente) {
        this.dataInicioGerente = dataInicioGerente;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
}
