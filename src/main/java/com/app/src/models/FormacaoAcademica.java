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
@Table(name = "formacoes_academicas")
public class FormacaoAcademica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "nivel", nullable = false)
    private String nivel;

    @Column(name = "sequencia_formacao", nullable = false)
    private int sequenciaFormacao;

    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "curso", nullable = false)
    private String curso;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "ano_inicio", nullable = false)
    private Integer anoInicio;

    @Column(name = "ano_coclusao", nullable = false)
    private Integer anoConclusao;

    @Column(name = "titulo_trabalho", nullable = false)
    private String tituloTrabalho;

    @Column(name = "orientador", nullable = false)
    private String orientador;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;
}
