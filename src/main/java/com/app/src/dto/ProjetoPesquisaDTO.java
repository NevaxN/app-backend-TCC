package com.app.src.dto;

import lombok.Getter;
import lombok.Setter;

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
