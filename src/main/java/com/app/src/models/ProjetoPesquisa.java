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
@Table(name = "projetos_pesquisa")
public class ProjetoPesquisa {
            
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "titulo", nullable = true)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = true)
    private String descricao;

    @Column(name = "instituicao", nullable = true)
    private String instituicao;

    @Column(name = "ano", nullable = true)
    private Integer ano;

    @Column(name = "financiador", nullable = true)
    private String financiador;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    @Column(name = "sequencia", nullable = true)
    private Integer sequencia;
}
