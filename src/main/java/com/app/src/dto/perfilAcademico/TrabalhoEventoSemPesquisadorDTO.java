package com.app.src.dto.perfilAcademico;

import com.app.src.dto.PesquisadorDTO;

public record TrabalhoEventoSemPesquisadorDTO(
        Integer sequenciaProducao,
        String autores,
        Integer ano,
        Boolean destaque,
        Integer id,
        String titulo,
        String classificacaoEvento,
        String nomeEvento,
        String cidadeEvento
) {
}
