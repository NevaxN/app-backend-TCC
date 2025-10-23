package com.app.src.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventoDTO {

    private Integer id;
    
    private PesquisadorDTO pesquisador;
    
    private String nomeEvento;
    
    private String tipo;
    
    private String tituloTrabalho;
    
    private Integer ano;
    
    private String local;
}
