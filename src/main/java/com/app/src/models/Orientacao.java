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

    @Column(name = "sequencia", nullable = true)
    private Integer sequencia;

    @Column(name = "tipo", nullable = true)
    private String tipo;

    @Column(name = "nome_orientado", nullable = true)
    private String nomeOrientado;

    @Column(name = "nome_curso", nullable = true)
    private String nomeCurso;

    @Column(name = "titulo_trabalho", nullable = true)
    private String tituloTrabalho;

    @Column(name = "instituicao", nullable = true)
    private String instituicao;

    @Column(name = "ano", nullable = true)
    private Integer ano;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;
}
