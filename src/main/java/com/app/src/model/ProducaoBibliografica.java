package com.app.src.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class ProducaoBibliografica {

    @Column(name = "sequencia_producao", nullable = false)
    private Integer sequenciaProducao;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "autores", columnDefinition = "TEXT", nullable = false)
    private String autores;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    public Integer getSequenciaProducao() {
        return sequenciaProducao;
    }

    public void setSequenciaProducao(Integer sequenciaProducao) {
        this.sequenciaProducao = sequenciaProducao;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

}
