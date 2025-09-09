package com.app.src.models;

import jakarta.persistence.*;

@Entity
@Table(name = "capitulos")
public class Capitulo extends ProducaoBibliografica {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo_capitulo", nullable = false)
    private String tituloCapitulo;

    @Column(name = "nome_livro", nullable = false)
    private String nomeLivro;

    @Column(name = "editora", nullable = false)
    private String editora;

    @Column(name = "idioma", nullable = false)
    private String idioma;

    @Column(name = "doi", nullable = false)
    private String doi;

    @Column(name = "pagina_inicial", nullable = false)
    private Integer paginaInicial;

    @Column(name = "pagina_final", nullable = false)
    private Integer paginaFinal;

    public Capitulo(String doi, String editora, Integer id, String idioma, String nomeLivro, Integer paginaFinal, Integer paginaInicial, String tituloCapitulo) {
        this.doi = doi;
        this.editora = editora;
        this.id = id;
        this.idioma = idioma;
        this.nomeLivro = nomeLivro;
        this.paginaFinal = paginaFinal;
        this.paginaInicial = paginaInicial;
        this.tituloCapitulo = tituloCapitulo;
    }

    public Capitulo() {
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
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

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public Integer getPaginaFinal() {
        return paginaFinal;
    }

    public void setPaginaFinal(Integer paginaFinal) {
        this.paginaFinal = paginaFinal;
    }

    public Integer getPaginaInicial() {
        return paginaInicial;
    }

    public void setPaginaInicial(Integer paginaInicial) {
        this.paginaInicial = paginaInicial;
    }

    public String getTituloCapitulo() {
        return tituloCapitulo;
    }

    public void setTituloCapitulo(String tituloCapitulo) {
        this.tituloCapitulo = tituloCapitulo;
    }
}
