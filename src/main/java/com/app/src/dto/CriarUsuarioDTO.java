package com.app.src.dto;

import java.util.Set;

public record CriarUsuarioDTO(
    String login,
    String password,
    String tipo_usuario,
    Set<String> roles) { // Para recebermos mais do que apenas um tipo de role
}
