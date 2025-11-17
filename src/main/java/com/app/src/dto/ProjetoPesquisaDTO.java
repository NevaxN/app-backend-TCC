package com.app.src.dto;

public record ProjetoPesquisaDTO (
        Integer id,

         PesquisadorDTO pesquisador,

         String titulo,

         String descricao,

         String instituicao,

         Integer ano,

         String financiador,

         Boolean destaque
){

}
