package com.example.pratica1.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Projeto {

    @Id
    private Integer pnumero;

    @Column(name = "projnome", nullable = false)
    private String projnome;

    @Column(name = "projlocal", nullable = false)
    private String projlocal;

    @ManyToOne
    @JoinColumn(name = "dnumero", nullable = false)
    private Departamento departamento;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL)
    private List<TrabalhaEm> trabalhos;

    // Getters e Setters

    public Integer getPnumero() {
        return pnumero;
    }

    public void setPnumero(Integer pnumero) {
        this.pnumero = pnumero;
    }

    public String getProjnome() {
        return projnome;
    }

    public void setProjnome(String projnome) {
        this.projnome = projnome;
    }

    public String getProjlocal() {
        return projlocal;
    }

    public void setProjlocal(String projlocal) {
        this.projlocal = projlocal;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<TrabalhaEm> getTrabalhos() {
        return trabalhos;
    }

    public void setTrabalhos(List<TrabalhaEm> trabalhos) {
        this.trabalhos = trabalhos;
    }
}

