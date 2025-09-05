package com.app.src.dto;

import com.app.src.models.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdiomaDTO {
    
    private Integer id;

    private Pesquisador pesquisador;
    
    private String idioma;
    
    private String leitura;
    
    private String escrita;
    
    private String fala;
}
