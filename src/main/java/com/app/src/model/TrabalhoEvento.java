package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trabalho_eventos")
public class TrabalhoEvento extends ProducaoBibliografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "classificacao_evento", nullable = false)
    private String classificacaoEvento;

    @Column(name = "nome_evento", nullable = false)
    private String nomeEvento;

    @Column(name = "cidade_evento", nullable = false)
    private String cidadeEvento;

    public TrabalhoEvento(String cidadeEvento, String classificacaoEvento, Integer id, String nomeEvento, String titulo) {
        this.cidadeEvento = cidadeEvento;
        this.id = id;
        this.nomeEvento = nomeEvento;
        this.titulo = titulo;
        this.classificacaoEvento = classificacaoEvento;
    }

    public TrabalhoEvento() {
    }

    public String getCidadeEvento() {
        return cidadeEvento;
    }

    public void setCidadeEvento(String cidadeEvento) {
        this.cidadeEvento = cidadeEvento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getClassificacaoEvento() {
        return classificacaoEvento;
    }

    public void setClassificacaoEvento(String classificacaoEvento) {
        this.classificacaoEvento = classificacaoEvento;
    }
}
