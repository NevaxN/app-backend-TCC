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
@Table(name = "atuacoes_profissionais")
public class AtuacaoProfissional {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sequencia_atuacao", nullable = false)
    private Integer sequenciaAtuacao;

    @Column(name = "sequencia_vinculo", nullable = false)
    private Integer sequenciaVinculo;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "ano_inicio", nullable = false)
    private Integer anoInicio;

    @Column(name = "ano_fim", nullable = false)
    private Integer anoFim;

    @Column(name = "mes_inicio", nullable = false)
    private Integer mesInicio;

    @Column(name = "mes_fim", nullable = false)
    private Integer mesFim;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;
}
