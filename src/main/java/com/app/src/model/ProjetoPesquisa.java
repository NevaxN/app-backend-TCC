package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "projetos_pesquisa")
public class ProjetoPesquisa {
            
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "financiador", nullable = false)
    private String financiador;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    @Column(name = "sequencia", nullable = false)
    private Integer sequencia;

    public ProjetoPesquisa() {}

    public ProjetoPesquisa(Pesquisador pesquisador, String titulo, String descricao, String instituicao,
                           Integer ano, String financiador, Boolean destaque, Integer sequencia) {
        this.pesquisador = pesquisador;
        this.titulo = titulo;
        this.descricao = descricao;
        this.instituicao = instituicao;
        this.ano = ano;
        this.financiador = financiador;
        this.destaque = destaque;
        this.sequencia = sequencia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getFinanciador() {
        return financiador;
    }

    public void setFinanciador(String financiador) {
        this.financiador = financiador;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }
}
