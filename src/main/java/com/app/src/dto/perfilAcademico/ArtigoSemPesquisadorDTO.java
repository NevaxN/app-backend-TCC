package com.app.src.dto.perfilAcademico;

public record ArtigoSemPesquisadorDTO (
        Integer sequenciaProducao,
        String autores,
        Integer ano,
        Boolean destaque,
        Integer id,
        String titulo,
        String periodico,
        String doi,
        String idioma
){
}
