package com.app.src.models;

import jakarta.persistence.*;

@Entity
@Table(name = "eventos")
public class Evento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "nome_evento", nullable = false)
    private String nomeEvento;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "titulo_trabalho", nullable = false)
    private String tituloTrabalho;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "local", nullable = false)
    private String local;

    public Evento() {}

    public Evento(Pesquisador pesquisador, String nomeEvento, String tipo, String tituloTrabalho, Integer ano, String local) {
        this.pesquisador = pesquisador;
        this.nomeEvento = nomeEvento;
        this.tipo = tipo;
        this.tituloTrabalho = tituloTrabalho;
        this.ano = ano;
        this.local = local;
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

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTituloTrabalho() {
        return tituloTrabalho;
    }

    public void setTituloTrabalho(String tituloTrabalho) {
        this.tituloTrabalho = tituloTrabalho;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
