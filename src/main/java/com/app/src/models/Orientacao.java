package com.app.src.models;

import jakarta.persistence.*;

@Entity
@Table(name = "orientacoes")
public class Orientacao {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "sequencia", nullable = false)
    private Integer sequencia;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "nome_orientado", nullable = false)
    private String nomeOrientado;

    @Column(name = "nome_curso", nullable = false)
    private String nomeCurso;

    @Column(name = "titulo_trabalho", nullable = false)
    private String tituloTrabalho;

    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    public Orientacao() {
    }

    public Orientacao(Pesquisador pesquisador, Integer sequencia, String tipo, String nomeOrientado, String tituloTrabalho,
                      String instituicao, Integer ano, Boolean destaque, String nomeCurso) {
        this.pesquisador = pesquisador;
        this.sequencia = sequencia;
        this.tipo = tipo;
        this.nomeOrientado = nomeOrientado;
        this.tituloTrabalho = tituloTrabalho;
        this.instituicao = instituicao;
        this.ano = ano;
        this.destaque = destaque;
        this.nomeCurso = nomeCurso;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeOrientado() {
        return nomeOrientado;
    }

    public void setNomeOrientado(String nomeOrientado) {
        this.nomeOrientado = nomeOrientado;
    }

    public String getTituloTrabalho() {
        return tituloTrabalho;
    }

    public void setTituloTrabalho(String tituloTrabalho) {
        this.tituloTrabalho = tituloTrabalho;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
}
