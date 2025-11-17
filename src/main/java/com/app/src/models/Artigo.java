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
@DiscriminatorValue("ARTIGO")
public class Artigo extends ProducaoBibliografica {

    @Column(name = "titulo", nullable = true)
    private String titulo;

    @Column(name = "periodico", nullable = true)
    private String periodico;
    
    @Column(name = "doi", nullable = true)
    private String doi;

    @Column(name = "idioma", nullable = true)
    private String idioma;
}
