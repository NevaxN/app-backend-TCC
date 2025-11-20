package com.app.src.dto;

public record UsuarioPreferenciasDTO(
        Integer usuarioId,
        Boolean exibirContato,
        Boolean exibirLocalizacao
) {
}
