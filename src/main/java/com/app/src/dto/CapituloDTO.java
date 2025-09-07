package com.app.src.dto;

import com.app.src.model.Pesquisador;

public record CapituloDTO(
         Integer sequenciaProducao,
         Pesquisador pesquisador,
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
