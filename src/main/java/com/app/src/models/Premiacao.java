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
@Table(name = "premiacoes")
public class Premiacao {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @Column(name = "titulo", nullable = true)
    private String titulo;

    @Column(name = "instituicao", nullable = true)
    private String instituicao;

    @Column(name = "ano", nullable = true)
    private Integer ano;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;
}
