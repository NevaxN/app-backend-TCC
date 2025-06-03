package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favoritos")
public class Favorito {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    public Favorito() {}

    public Favorito(Pesquisador pesquisador){
        this.pesquisador = pesquisador;
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
}
