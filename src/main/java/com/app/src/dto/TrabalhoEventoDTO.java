package com.app.src.dto;

import com.app.src.models.Pesquisador;

public record TrabalhoEventoDTO(
        Integer sequenciaProducao,
        PesquisadorDTO pesquisador,
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
