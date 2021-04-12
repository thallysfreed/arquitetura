package com.poc.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Compiliance.
 */
@Entity
@Table(name = "compiliance")
public class Compiliance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "setor")
    private String setor;

    @Column(name = "industrial")
    private Boolean industrial;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Compiliance nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Compiliance descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSetor() {
        return setor;
    }

    public Compiliance setor(String setor) {
        this.setor = setor;
        return this;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public Boolean isIndustrial() {
        return industrial;
    }

    public Compiliance industrial(Boolean industrial) {
        this.industrial = industrial;
        return this;
    }

    public void setIndustrial(Boolean industrial) {
        this.industrial = industrial;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Compiliance dataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compiliance)) {
            return false;
        }
        return id != null && id.equals(((Compiliance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Compiliance{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", setor='" + getSetor() + "'" +
            ", industrial='" + isIndustrial() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            "}";
    }
}
