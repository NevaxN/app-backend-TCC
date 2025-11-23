package com.app.src.dto.perfilAcademico;

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
