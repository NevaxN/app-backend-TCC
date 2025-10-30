package com.app.src.dto;

import java.util.Set;

public record CriarUsuarioDTO(
    Integer id,
    String login,
    String password,
    String tipo_usuario,
    Set<String> roles
) {}