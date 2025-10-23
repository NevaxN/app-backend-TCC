package com.app.src.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoPesquisaDTO {
    private Integer id;

    private PesquisadorDTO pesquisador;
    
    private String titulo;
    
    private String descricao;
    
    private String instituicao;
    
    private Integer ano;
    
    private String financiador;
    
    private Boolean destaque;
}
