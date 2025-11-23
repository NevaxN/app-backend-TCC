package com.app.src.dto;

public record EnviarContatoDTO(
        String texto,
        Integer idRemetente,
        String tipoRemetente,
        Integer idDestinatario
) {
}