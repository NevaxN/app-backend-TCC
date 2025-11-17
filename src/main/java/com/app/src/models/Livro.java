package com.app.src.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("LIVRO")
public class Livro extends ProducaoBibliografica {

    @Column(name = "titulo", nullable = true)
    private String titulo;

    @Column(name = "isbn", nullable = true)
    private String isbn;

    @Column(name = "editora", nullable = true)
    private String editora;

    @Column(name = "idioma", nullable = true)
    private String idioma;

    @Column(name = "numero_paginas", nullable = true)
    private Integer numeroPaginas;
}
