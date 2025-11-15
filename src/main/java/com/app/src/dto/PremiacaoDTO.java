package com.app.src.dto;

public record PremiacaoDTO(
        Integer id,
        PesquisadorDTO pesquisador,
        String titulo,
        String instituicao,
        Integer ano,
        Boolean destaque
) {}
