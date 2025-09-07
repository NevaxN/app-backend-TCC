package com.app.src.dto;

import com.app.src.model.Pesquisador;

public record TrabalhoEventoDTO(
        Integer sequenciaProducao,
        Pesquisador pesquisador,
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
