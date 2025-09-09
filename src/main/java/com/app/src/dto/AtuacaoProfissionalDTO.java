package com.app.src.dto;

import com.app.src.models.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtuacaoProfissionalDTO {
    
    private Integer id;
    
    private Pesquisador pesquisador;
    
    private String instituicao;
    
    private String cargo;
    
    private Integer anoInicio;
    
    private Integer anoFim;
    
    private Boolean destaque;

    private Integer sequenciaAtuacao;

    private Integer sequenciaVinculo;

    private Integer mesInicio;

    private Integer mesFim;
}
