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
    
    private String vinculo;
    
    private String departamento;
    
    private String cargo;
    
    private Integer anoInicio;
    
    private Integer anoFim;
    
    private Boolean destaque;
}
