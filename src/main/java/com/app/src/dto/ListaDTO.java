package com.app.src.dto;

import java.util.Set;

import com.app.src.models.Pesquisador;
import com.app.src.models.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaDTO {
    
    private Integer id;

    private Pesquisador pesquisador;
    
    private String nomeLista;

    private Set<Usuario> perfisSalvos;

}
