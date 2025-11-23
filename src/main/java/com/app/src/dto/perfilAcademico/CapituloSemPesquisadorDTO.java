package com.app.src.dto.perfilAcademico;

public record CapituloSemPesquisadorDTO(
        Integer sequenciaProducao,
        String autores,
        Integer ano,
        Boolean destaque,
        Integer id,
        String tituloCapitulo,
        String nomeLivro,
        String editora,
        String idioma,
        String doi,
        Integer paginaInicial,
        Integer paginaFinal
) {
}
