package com.app.src.models;

import jakarta.persistence.*;

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

    public AtuacaoProfissional() {
    }

    public AtuacaoProfissional(Integer anoFim, Integer anoInicio, String cargo, Boolean destaque, Integer id, String instituicao, Integer mesFim, Integer mesInicio, Pesquisador pesquisador, Integer sequenciaAtuacao, Integer sequenciaVinculo) {
        this.anoFim = anoFim;
        this.anoInicio = anoInicio;
        this.cargo = cargo;
        this.destaque = destaque;
        this.id = id;
        this.instituicao = instituicao;
        this.mesFim = mesFim;
        this.mesInicio = mesInicio;
        this.pesquisador = pesquisador;
        this.sequenciaAtuacao = sequenciaAtuacao;
        this.sequenciaVinculo = sequenciaVinculo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(Integer anoInicio) {
        this.anoInicio = anoInicio;
    }

    public Integer getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(Integer anoFim) {
        this.anoFim = anoFim;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public Integer getMesFim() {
        return mesFim;
    }

    public void setMesFim(Integer mesFim) {
        this.mesFim = mesFim;
    }

    public Integer getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(Integer mesInicio) {
        this.mesInicio = mesInicio;
    }

    public Integer getSequenciaAtuacao() {
        return sequenciaAtuacao;
    }

    public void setSequenciaAtuacao(Integer sequenciaAtuacao) {
        this.sequenciaAtuacao = sequenciaAtuacao;
    }

    public Integer getSequenciaVinculo() {
        return sequenciaVinculo;
    }

    public void setSequenciaVinculo(Integer sequenciaVinculo) {
        this.sequenciaVinculo = sequenciaVinculo;
    }
}
