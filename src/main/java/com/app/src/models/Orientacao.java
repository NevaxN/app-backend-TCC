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
@Table(name = "orientacoes")
public class Orientacao {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "sequencia", nullable = false)
    private Integer sequencia;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "nome_orientado", nullable = false)
    private String nomeOrientado;

    @Column(name = "nome_curso", nullable = false)
    private String nomeCurso;

    @Column(name = "titulo_trabalho", nullable = false)
    private String tituloTrabalho;

    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;
}
