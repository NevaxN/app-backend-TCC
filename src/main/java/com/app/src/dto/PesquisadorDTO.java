package com.app.src.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.app.src.models.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisadorDTO {
    private Integer id;

    private Usuario usuario;

    private String nomePesquisador;

    private String sobrenome;

    private LocalDate dataNascimento;

    private String nomeCitacoesBibliograficas;

    private LocalDate dataAtualizacao;

    private LocalTime horaAtualizacao;

    private String nacionalidade;

    private String paisNascimento;

    private Long lattesId;

    private byte[] imagemPerfil;
}
