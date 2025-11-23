package com.app.src.models;

import java.sql.Types;

import org.hibernate.annotations.JdbcTypeCode;

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
@Table(name = "empresas")
public class Empresa {
           
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "nomeRegistro", nullable = false)
    private String nomeRegistro;

    @Column(name = "nomeComercial", nullable = false)
    private String nomeComercial;

    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Column(name = "numeroEndereco", nullable = false)
    private String numeroEndereco;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "site", nullable = true)
    private String site;

    @Column(name = "setor", nullable = false)
    private String setor;

    @Column(name = "frase", nullable = true)
    private String frase;

    @Column(name = "textoEmpresa", nullable = true)
    private String textoEmpresa;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column()
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] imagemPerfil;
}
