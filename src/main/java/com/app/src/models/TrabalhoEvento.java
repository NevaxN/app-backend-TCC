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
@DiscriminatorValue("TRABALHO_EVENTO")
public class TrabalhoEvento extends ProducaoBibliografica {

    @Column(name = "titulo", nullable = true)
    private String titulo;

    @Column(name = "classificacao_evento", nullable = true)
    private String classificacaoEvento;

    @Column(name = "nome_evento", nullable = true)
    private String nomeEvento;

    @Column(name = "cidade_evento", nullable = true)
    private String cidadeEvento;
}
