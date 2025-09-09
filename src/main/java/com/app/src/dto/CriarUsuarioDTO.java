package com.app.src.dto;

import com.app.src.auth.models.RoleName;


public record CriarUsuarioDTO(    
    String login,
    String password,
    RoleName role) {
}
