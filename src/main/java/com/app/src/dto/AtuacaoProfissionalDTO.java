package com.app.src.dto;

import com.app.src.models.Pesquisador;


public record AtuacaoProfissionalDTO(
        Integer id,
        Pesquisador pesquisador,
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

