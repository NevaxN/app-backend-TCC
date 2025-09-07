package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "artigos")
public class Artigo extends ProducaoBibliografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "periodico", nullable = false)
    private String periodico;
    
    @Column(name = "doi", nullable = false)
    private String doi;

    @Column(name = "idioma", nullable = false)
    private String idioma;

    public Artigo(String doi, Integer id, String idioma, String periodico, String titulo) {
        super();
        this.doi = doi;
        this.id = id;
        this.idioma = idioma;
        this.periodico = periodico;
        this.titulo = titulo;
    }

    public Artigo() {
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getPeriodico() {
        return periodico;
    }

    public void setPeriodico(String periodico) {
        this.periodico = periodico;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
