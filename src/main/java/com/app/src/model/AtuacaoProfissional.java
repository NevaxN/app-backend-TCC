package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "atuacoes_profissionais")
public class AtuacaoProfissional {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "vinculo", nullable = false)
    private String vinculo;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "ano_inicio", nullable = false)
    private Integer anoInicio;

    @Column(name = "ano_fim", nullable = false)
    private Integer anoFim;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    public AtuacaoProfissional() {
    }

    public AtuacaoProfissional(Pesquisador pesquisador, String instituicao, String vinculo, String departamento,
                               String cargo, Integer anoInicio, Integer anoFim, Boolean destaque) {
        this.pesquisador = pesquisador;
        this.instituicao = instituicao;
        this.vinculo = vinculo;
        this.departamento = departamento;
        this.cargo = cargo;
        this.anoInicio = anoInicio;
        this.anoFim = anoFim;
        this.destaque = destaque;
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

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
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
}
