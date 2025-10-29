package com.app.src.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Integer id;
    private String login;
    private Set<String> roles;
    private String tipoUsuario;
}
