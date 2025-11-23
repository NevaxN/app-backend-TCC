package com.app.src.dto.perfilAcademico;

import com.app.src.dto.PesquisadorDTO;

public record PremiacaoSemPesquisadorDTO(
        Integer id,
        String titulo,
        String instituicao,
        Integer ano,
        Boolean destaque
) {
}
