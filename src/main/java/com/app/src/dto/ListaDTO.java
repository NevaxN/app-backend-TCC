package com.app.src.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaDTO {
    
    private Integer id;

    private PesquisadorDTO pesquisador;
    
    private String nomeLista;

    private Set<PerfilSalvoDTO> perfisSalvos;

}
