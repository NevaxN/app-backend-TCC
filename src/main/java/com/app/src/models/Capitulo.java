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
@DiscriminatorValue("CAPITULO")
public class Capitulo extends ProducaoBibliografica {

    @Column(name = "titulo_capitulo", nullable = true)
    private String tituloCapitulo;

    @Column(name = "nome_livro", nullable = true)
    private String nomeLivro;

    @Column(name = "editora", nullable = true)
    private String editora;

    @Column(name = "idioma", nullable = true)
    private String idioma;

    @Column(name = "doi", nullable = true)
    private String doi;

    @Column(name = "pagina_inicial", nullable = true)
    private Integer paginaInicial;

    @Column(name = "pagina_final", nullable = true)
    private Integer paginaFinal;
}
