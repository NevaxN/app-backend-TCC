package com.app.src.dto;

public record CapituloDTO(
         Integer sequenciaProducao,
         PesquisadorDTO pesquisador,
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
