package com.app.src.dto;

import java.util.List;

import com.app.src.auth.enums.Role;

public record ResgatarUsuarioDTO(
    Integer id,
    String email,
    List<Role> roles
) {
}
