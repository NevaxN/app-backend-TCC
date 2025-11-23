package com.app.src.dto.perfilAcademico;

public record PremiacaoSemPesquisadorDTO(
        Integer id,
        String titulo,
        String instituicao,
        Integer ano,
        Boolean destaque
) {
}
