package com.poc.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A NormaTecnica.
 */
@Entity
@Table(name = "norma_tecnica")
public class NormaTecnica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "industrial")
    private Boolean industrial;

    @Column(name = "setor")
    private String setor;

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

    public NormaTecnica nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public NormaTecnica descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isIndustrial() {
        return industrial;
    }

    public NormaTecnica industrial(Boolean industrial) {
        this.industrial = industrial;
        return this;
    }

    public void setIndustrial(Boolean industrial) {
        this.industrial = industrial;
    }

    public String getSetor() {
        return setor;
    }

    public NormaTecnica setor(String setor) {
        this.setor = setor;
        return this;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public NormaTecnica dataCriacao(LocalDate dataCriacao) {
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
        if (!(o instanceof NormaTecnica)) {
            return false;
        }
        return id != null && id.equals(((NormaTecnica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NormaTecnica{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", industrial='" + isIndustrial() + "'" +
            ", setor='" + getSetor() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            "}";
    }
}
