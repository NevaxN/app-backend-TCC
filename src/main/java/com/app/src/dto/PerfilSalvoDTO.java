package com.app.src.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilSalvoDTO {
    private Integer idUsuario;
    private Integer idEntidade;
    private String nomeCompleto;
    private String tipoPerfil;
    private String area;
}
