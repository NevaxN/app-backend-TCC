package com.app.src.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "producao_bibliografica")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_producao")
public class ProducaoBibliografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sequencia_producao", nullable = true)
    private Integer sequenciaProducao;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    @JsonIgnore
    private Pesquisador pesquisador;

    @Column(name = "tipo", nullable = true)
    private String tipo;

//    @Column(name = "titulo", nullable = true)
//    private String titulo;

    @Column(name = "autores", columnDefinition = "TEXT", nullable = true)
    private String autores;

    @Column(name = "ano", nullable = true)
    private Integer ano;

//    @Column(name = "veiculo_publicacao", nullable = true)
//    private String veiculoPublicacao;
//
//    @Column(name = "issn", nullable = true)
//    private String issn;
//
//    @Column(name = "doi", nullable = true)
//    private String doi;

    @Column(name = "destaque", nullable = true)
    private Boolean destaque;
}
