package com.app.src.dto;

import com.app.src.models.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventoDTO {

    private Integer id;
    
    private Pesquisador pesquisador;
    
    private String nomeEvento;
    
    private String tipo;
    
    private String tituloTrabalho;
    
    private Integer ano;
    
    private String local;
}
