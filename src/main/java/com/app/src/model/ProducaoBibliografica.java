package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producoes_bibliograficas")
public class ProducaoBibliografica {
            
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;
    
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "veiculo_publicacao", nullable = false)
    private String veiculoPublicacao;

    @Column(name = "issn", nullable = false)
    private String issn;

    @Column(name = "doi", nullable = false)
    private String doi;

    @Column(name = "autores", columnDefinition = "TEXT", nullable = false)
    private String autores;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    public ProducaoBibliografica() {}

    public ProducaoBibliografica(Pesquisador pesquisador, String tipo, String titulo, Integer ano,
                                 String veiculoPublicacao, String issn, String doi, String autores, Boolean destaque) {
        this.pesquisador = pesquisador;
        this.tipo = tipo;
        this.titulo = titulo;
        this.ano = ano;
        this.veiculoPublicacao = veiculoPublicacao;
        this.issn = issn;
        this.doi = doi;
        this.autores = autores;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getVeiculoPublicacao() {
        return veiculoPublicacao;
    }

    public void setVeiculoPublicacao(String veiculoPublicacao) {
        this.veiculoPublicacao = veiculoPublicacao;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }
}
