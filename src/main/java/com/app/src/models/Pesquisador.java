package com.app.src.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pesquisadores")
public class Pesquisador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
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
    private Long lattesId;

    @Column()
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] imagemPerfil;
}
