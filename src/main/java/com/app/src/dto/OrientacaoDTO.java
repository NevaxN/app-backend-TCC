package com.app.src.dto;

import com.app.src.models.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrientacaoDTO {
    
    private Integer id;
    
    private Pesquisador pesquisador;
    
    private String tipo;
    
    private String nomeOrientado;

    private String nomeCurso;

    private String tituloTrabalho;
    
    private String instituicao;
    
    private Integer ano;

    private Integer sequencia;
    
    private Boolean destaque;
}
