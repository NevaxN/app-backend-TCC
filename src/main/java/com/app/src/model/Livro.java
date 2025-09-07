package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro extends ProducaoBibliografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "editora", nullable = false)
    private String editora;

    @Column(name = "idioma", nullable = false)
    private String idioma;

    @Column(name = "numero_paginas", nullable = false)
    private Integer numeroPaginas;

    public Livro(String editora, Integer id, String idioma, String isbn, String titulo, Integer numeroPaginas) {
        this.editora = editora;
        this.id = id;
        this.idioma = idioma;
        this.isbn = isbn;
        this.titulo = titulo;
        this.numeroPaginas = numeroPaginas;
    }

    public Livro() {

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
}
