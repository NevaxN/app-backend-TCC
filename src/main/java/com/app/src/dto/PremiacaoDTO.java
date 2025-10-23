package com.app.src.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiacaoDTO {

    private Integer id;
    
    private PesquisadorDTO pesquisador;
    
    private String titulo;
    
    private String instituicao;
    
    private Integer ano;

}
