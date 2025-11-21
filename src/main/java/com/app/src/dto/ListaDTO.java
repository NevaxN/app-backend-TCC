package com.app.src.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaDTO {
    
    private Integer id;
    private UsuarioDTO Usuario;
    private String nomeLista;
    private Set<PerfilSalvoDTO> perfisSalvos;

}
