package com.app.src.dto.perfilAcademico;

import com.app.src.dto.PesquisadorDTO;

public record OrientacoesSemPesquisadorDTO(
        Integer id,
        String tipo,
        String nomeOrientado,
        String nomeCurso,
        String tituloTrabalho,
        String instituicao,
        Integer ano,
        Integer sequencia,
        Boolean destaque
) {
}
