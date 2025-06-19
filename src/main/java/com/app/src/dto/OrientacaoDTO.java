package com.app.src.dto;

import com.app.src.model.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrientacaoDTO {
    
    private Integer id;
    
    private Pesquisador pesquisador;
    
    private String tipo;
    
    private String nomeOrientado;
    
    private String tituloTrabalho;
    
    private String instituicao;
    
    private Integer anoInicio;
    
    private Integer anoFim;
    
    private Boolean destaque;
}
