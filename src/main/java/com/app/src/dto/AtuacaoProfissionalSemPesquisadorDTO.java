package com.app.src.dto;

public record AtuacaoProfissionalSemPesquisadorDTO(
        Integer id,
        String instituicao,
        String cargo,
        Integer anoInicio,
        Integer anoFim,
        Boolean destaque,
        Integer sequenciaAtuacao,
        Integer sequenciaVinculo,
        Integer mesInicio,
        Integer mesFim
) {
}
