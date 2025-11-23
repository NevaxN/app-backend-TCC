package com.app.src.dto.perfilAcademico;

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
