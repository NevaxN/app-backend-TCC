package com.app.src.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @ElementCollection
    @CollectionTable(
        name = "tag_lista_tags",
        joinColumns = @JoinColumn(name = "tag_id")
    )
    @Column(name = "tag")
    private List<String> listaTags;

    public Tag() {}

    public Tag(Pesquisador pesquisador, List<String> listaTags) {
        this.pesquisador = pesquisador;
        this.listaTags = listaTags;
    }

    // Getters e setters

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

    public List<String> getListaTags() {
        return listaTags;
    }

    public void setListaTags(List<String> listaTags) {
        this.listaTags = listaTags;
    }
    
}
