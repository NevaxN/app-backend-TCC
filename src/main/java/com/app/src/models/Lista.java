package com.app.src.models;

import jakarta.persistence.*;

@Entity
@Table(name = "listas")
public class Lista {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "nome_lista", nullable = false)
    private String nomeLista;

    public Lista() {}

    public Lista(Pesquisador pesquisador, String nomeLista) {
        this.pesquisador = pesquisador;
        this.nomeLista = nomeLista;
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

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }
}
