package com.app.src.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pesquisadores")
public class Pesquisador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Chave estrangeira para a tabela Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "nome_pesquisador", nullable = false)
    private String nomePesquisador;

    @Column(nullable = false)
    private String sobrenome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "nome_citacoes_bibliograficas")
    private String nomeCitacoesBibliograficas;

    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

    @Column(name = "hora_atualizacao")
    private LocalTime horaAtualizacao;

    private String nacionalidade;

    @Column(name = "pais_nascimento")
    private String paisNascimento;

    @Column(name = "lattes_id")
    private Integer lattesId;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomePesquisador() {
        return nomePesquisador;
    }

    public void setNomePesquisador(String nomePesquisador) {
        this.nomePesquisador = nomePesquisador;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeCitacoesBibliograficas() {
        return nomeCitacoesBibliograficas;
    }

    public void setNomeCitacoesBibliograficas(String nomeCitacoesBibliograficas) {
        this.nomeCitacoesBibliograficas = nomeCitacoesBibliograficas;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public LocalTime getHoraAtualizacao() {
        return horaAtualizacao;
    }

    public void setHoraAtualizacao(LocalTime horaAtualizacao) {
        this.horaAtualizacao = horaAtualizacao;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getPaisNascimento() {
        return paisNascimento;
    }

    public void setPaisNascimento(String paisNascimento) {
        this.paisNascimento = paisNascimento;
    }

    public Integer getLattesId() {
        return lattesId;
    }

    public void setLattesId(Integer lattesId) {
        this.lattesId = lattesId;
    }
}
