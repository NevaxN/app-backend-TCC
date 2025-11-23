package com.app.src.dto;

public record ArtigoDTO (
         Integer sequenciaProducao,
         PesquisadorDTO pesquisador,
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
