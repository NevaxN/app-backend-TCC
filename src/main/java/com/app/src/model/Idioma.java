package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "idiomas")
public class Idioma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "idioma", nullable = false)
    private String idioma;

    @Column(name = "leitura", nullable = false)
    private String leitura;

    @Column(name = "escrita", nullable = false)
    private String escrita;

    @Column(name = "fala", nullable = false)
    private String fala;

    public Idioma() {}

    public Idioma(Pesquisador pesquisador, String idioma, String leitura, String escrita, String fala) {
        this.pesquisador = pesquisador;
        this.idioma = idioma;
        this.leitura = leitura;
        this.escrita = escrita;
        this.fala = fala;
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getLeitura() {
        return leitura;
    }

    public void setLeitura(String leitura) {
        this.leitura = leitura;
    }

    public String getEscrita() {
        return escrita;
    }

    public void setEscrita(String escrita) {
        this.escrita = escrita;
    }

    public String getFala() {
        return fala;
    }

    public void setFala(String fala) {
        this.fala = fala;
    }
}
