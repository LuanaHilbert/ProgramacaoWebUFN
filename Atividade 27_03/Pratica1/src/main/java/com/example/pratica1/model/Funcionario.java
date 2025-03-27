package com.example.pratica1.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Funcionario {

    @Id
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String pnome;

    @Column(length = 1)
    private String minicial;

    @Column(nullable = false)
    private String unome;

    @Column(nullable = false)
    private LocalDate dataNasc;

    @Column(nullable = false)
    private Double salario;

    @Column(length = 1)
    private String sexo;

    @Column
    private String endereco;

    // Relacionamento de auto-referência: supervisor
    @ManyToOne
    @JoinColumn(name = "cpf_supervisor")
    private Funcionario supervisor;

    @OneToMany(mappedBy = "supervisor")
    private List<Funcionario> subordinados;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Dependente> dependentes;

    // Departamento em que o funcionário trabalha
    @ManyToOne
    @JoinColumn(name = "dnumero", nullable = false)
    private Departamento departamento;

    // Departamento que o funcionário gerencia (opcional, 0..1)
    @OneToOne(mappedBy = "gerente")
    private Departamento departamentoGerenciado;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<TrabalhaEm> trabalhos;

    // Getter "virtual" para nome completo
    @Transient
    public String getNomeCompleto() {
        return pnome + " " + (minicial != null ? minicial + ". " : "") + unome;
    }

    // Getters e Setters

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPnome() {
        return pnome;
    }

    public void setPnome(String pnome) {
        this.pnome = pnome;
    }

    public String getMinicial() {
        return minicial;
    }

    public void setMinicial(String minicial) {
        this.minicial = minicial;
    }

    public String getUnome() {
        return unome;
    }

    public void setUnome(String unome) {
        this.unome = unome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Funcionario getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Funcionario supervisor) {
        this.supervisor = supervisor;
    }

    public List<Funcionario> getSubordinados() {
        return subordinados;
    }

    public void setSubordinados(List<Funcionario> subordinados) {
        this.subordinados = subordinados;
    }

    public List<Dependente> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Departamento getDepartamentoGerenciado() {
        return departamentoGerenciado;
    }

    public void setDepartamentoGerenciado(Departamento departamentoGerenciado) {
        this.departamentoGerenciado = departamentoGerenciado;
    }

    public List<TrabalhaEm> getTrabalhos() {
        return trabalhos;
    }

    public void setTrabalhos(List<TrabalhaEm> trabalhos) {
        this.trabalhos = trabalhos;
    }

}
