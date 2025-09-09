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
@Table(name = "trabalho_eventos")
public class TrabalhoEvento extends ProducaoBibliografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "classificacao_evento", nullable = false)
    private String classificacaoEvento;

    @Column(name = "nome_evento", nullable = false)
    private String nomeEvento;

    @Column(name = "cidade_evento", nullable = false)
    private String cidadeEvento;
}
