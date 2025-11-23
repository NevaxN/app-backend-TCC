package com.app.src.dto.perfilAcademico;

public record ProjetoPesquisaSemPesquisadorDTO(
        Integer id,
        String titulo,
        String descricao,
        String instituicao,
        Integer ano,
        String financiador,
        Boolean destaque
) {
}
