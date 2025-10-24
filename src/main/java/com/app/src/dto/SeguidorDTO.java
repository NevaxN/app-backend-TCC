package com.app.src.dto;

import com.app.src.models.Pesquisador;
import com.app.src.models.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeguidorDTO {
    private Integer id;
    private Pesquisador pesquisador;
    private Usuario usuario;
}
