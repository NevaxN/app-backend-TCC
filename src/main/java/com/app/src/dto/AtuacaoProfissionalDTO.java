package com.app.src.dto;

public record AtuacaoProfissionalDTO(
        Integer id,
        PesquisadorDTO pesquisador,
        String instituicao,
        String cargo,
        Integer anoInicio,
        Integer anoFim,
        Boolean destaque,
        Integer sequenciaAtuacao,
        Integer sequenciaVinculo,
        Integer mesInicio,
        Integer mesFim
) {}

