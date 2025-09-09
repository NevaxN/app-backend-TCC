package com.app.src.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "producao_bibliografica")
@Getter
@Setter
public class ProducaoBibliografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sequencia_producao", nullable = false)
    private Integer sequenciaProducao;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "autores", columnDefinition = "TEXT", nullable = false)
    private String autores;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "veiculo_publicacao", nullable = false)
    private String veiculoPublicacao;

    @Column(name = "issn", nullable = false)
    private String issn;

    @Column(name = "doi", nullable = false)
    private String doi;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;
}
