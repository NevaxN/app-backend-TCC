package com.app.src.dto.perfilAcademico;

import com.app.src.dto.PesquisadorDTO;

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
