package com.example.pratica1.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TrabalhaEmId implements Serializable {

    private String funcionarioCpf;
    private Integer projetoPnumero;

    public TrabalhaEmId() {}

    public TrabalhaEmId(String funcionarioCpf, Integer projetoPnumero) {
        this.funcionarioCpf = funcionarioCpf;
        this.projetoPnumero = projetoPnumero;
    }

    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    public Integer getProjetoPnumero() {
        return projetoPnumero;
    }

    public void setProjetoPnumero(Integer projetoPnumero) {
        this.projetoPnumero = projetoPnumero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrabalhaEmId that)) return false;
        return Objects.equals(funcionarioCpf, that.funcionarioCpf) &&
                Objects.equals(projetoPnumero, that.projetoPnumero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(funcionarioCpf, projetoPnumero);
    }
}

