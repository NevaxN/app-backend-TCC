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
@Table(name = "idiomas")
public class Idioma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "idioma", nullable = false)
    private String idioma;

    @Column(name = "leitura", nullable = false)
    private String leitura;

    @Column(name = "escrita", nullable = false)
    private String escrita;

    @Column(name = "fala", nullable = false)
    private String fala;
}
