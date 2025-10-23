package com.app.src.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdiomaDTO {
    
    private Integer id;

    private PesquisadorDTO pesquisador;
    
    private String idioma;
    
    private String leitura;
    
    private String escrita;
    
    private String fala;
}
