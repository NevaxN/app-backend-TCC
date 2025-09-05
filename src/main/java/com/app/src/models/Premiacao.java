package com.app.src.models;

import jakarta.persistence.*;

@Entity
@Table(name = "premiacoes")
public class Premiacao {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    public Premiacao() {}

    public Premiacao(Pesquisador pesquisador, String titulo, String instituicao, Integer ano) {
        this.pesquisador = pesquisador;
        this.titulo = titulo;
        this.instituicao = instituicao;
        this.ano = ano;
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
}
