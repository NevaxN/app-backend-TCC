package com.app.src.dto;

import com.app.src.models.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoPesquisaDTO {
    private Integer id;

    private Pesquisador pesquisador;
    
    private String titulo;
    
    private String descricao;
    
    private String instituicao;
    
    private Integer anoInicio;
    
    private Integer anoFim;
    
    private String financiador;
    
    private Boolean destaque;
}
