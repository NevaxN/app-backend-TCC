package com.app.src.dto;

public record OrientacaoDTO(
        Integer id,
        PesquisadorDTO pesquisador,
        String tipo,
        String nomeOrientado,
        String nomeCurso,
        String tituloTrabalho,
        String instituicao,
        Integer ano,
        Integer sequencia,
        Boolean destaque
) {}
