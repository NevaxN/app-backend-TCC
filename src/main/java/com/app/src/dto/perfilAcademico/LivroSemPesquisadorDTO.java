package com.app.src.dto.perfilAcademico;

import com.app.src.dto.PesquisadorDTO;

public record LivroSemPesquisadorDTO(
        Integer sequenciaProducao,
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
