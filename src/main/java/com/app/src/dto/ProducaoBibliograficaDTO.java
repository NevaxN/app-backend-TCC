package com.app.src.dto;

import com.app.src.model.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducaoBibliograficaDTO {
    private Integer id;

    private Pesquisador pesquisador;
    
    private String tipo;
    
    private String titulo;
    
    private Integer ano;
    
    private String veiculoPublicacao;
    
    private String issn;
    
    private String doi;
    
    private String autores;
    
    private Boolean destaque;
}
