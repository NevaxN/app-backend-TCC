package com.app.src.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "pesquisadores")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pesquisador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "nome_pesquisador", nullable = false)
    private String nomePesquisador;

    @Column(nullable = false)
    private String sobrenome;

    @Column(name = "data_nascimento")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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

    @JsonIgnore
    @OneToMany(mappedBy = "pesquisador")
    private Set<Seguidor> seguidores;

    @Column()
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] imagemPerfil;
}
