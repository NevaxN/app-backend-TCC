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
}
