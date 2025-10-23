package com.app.src.dto;

import com.app.src.models.Pesquisador;

public record LivroDTO(
        Integer sequenciaProducao,
        PesquisadorDTO pesquisador,
        String autores,
        String isbn,
        String editora,
        Integer ano,
        Integer numeroPaginas,
        Boolean destaque,
        Integer id,
        String idioma,
        String titulo
) {
}
