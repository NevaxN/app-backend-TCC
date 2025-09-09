package com.app.src.models;

import jakarta.persistence.*;

@Entity
@Table(name = "formacoes_academicas")
public class FormacaoAcademica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "nivel", nullable = false)
    private String nivel;

    @Column(name = "sequencia_formacao", nullable = false)
    private int sequenciaFormacao;

    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "curso", nullable = false)
    private String curso;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "ano_inicio", nullable = false)
    private Integer anoInicio;

    @Column(name = "ano_coclusao", nullable = false)
    private Integer anoConclusao;

    @Column(name = "titulo_trabalho", nullable = false)
    private String tituloTrabalho;

    @Column(name = "orientador", nullable = false)
    private String orientador;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    public FormacaoAcademica() {
    }

    public FormacaoAcademica(Pesquisador pesquisador, String nivel, int sequenciaFormacao, String instituicao, String curso, String status,
                             Integer anoInicio, Integer anoConclusao, String tituloTrabalho, String orientador, Boolean destaque) {
        this.pesquisador = pesquisador;
        this.nivel = nivel;
        this.sequenciaFormacao = sequenciaFormacao;
        this.instituicao = instituicao;
        this.curso = curso;
        this.status = status;
        this.anoInicio = anoInicio;
        this.anoConclusao = anoConclusao;
        this.tituloTrabalho = tituloTrabalho;
        this.orientador = orientador;
        this.destaque = destaque;
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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(Integer anoInicio) {
        this.anoInicio = anoInicio;
    }

    public Integer getAnoConclusao() {
        return anoConclusao;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }

    public String getTituloTrabalho() {
        return tituloTrabalho;
    }

    public void setTituloTrabalho(String tituloTrabalho) {
        this.tituloTrabalho = tituloTrabalho;
    }

    public String getOrientador() {
        return orientador;
    }

    public void setOrientador(String orientador) {
        this.orientador = orientador;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public int getSequenciaFormacao() {
        return sequenciaFormacao;
    }

    public void setSequenciaFormacao(int sequenciaFormacao) {
        this.sequenciaFormacao = sequenciaFormacao;
    }
}
